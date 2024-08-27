package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.ProductRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.AuthenticationHelper;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderCreateServiceHelper {

    private final ProductRepository productRepository;
    private final AuthenticationHelper authenticationHelper;
    private final OrderValidator validator;
    private final ProductServicesFacade productServicesFacade;

    Order prepareOrderToSave(CreateOrderRequestDTO orderDTO, Order order) {

        order.setOrderLines(getOrderLinesFromOrderDTO(orderDTO.getOrderLineDTOs(), order));
        order.setTotalPrice(getTotalOrderPrice(order.getOrderLines()));
        order.setEmployeeId(authenticationHelper.getSellerId());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setStatusUpdatedAt(LocalDateTime.now());

        return order;
    }

    private List<OrderLine> getOrderLinesFromOrderDTO(List<OrderLineDTO> orderLineDTOs, Order order) {
        List<OrderLine> orderLines = new ArrayList<>();
        Map<Long, Product> productsFromDB = getProductsFromOrderLines(orderLineDTOs);

        validator.validateOrderLineDTOS(orderLineDTOs, productsFromDB);

        for (OrderLineDTO orderLineDTO : orderLineDTOs) {
            Long productId = orderLineDTO.productId();
            Product productById = productsFromDB.get(productId);
            orderLines.add(OrderLine.builder()
                    .order(order)
                    .product(productById)
                    .quantity(orderLineDTO.quantity())
                    .build());
        }

        productServicesFacade.decreaseProductQuantity(orderLineDTOs);
        return orderLines;
    }

    private BigDecimal getTotalOrderPrice(List<OrderLine> orderLines) {
        return orderLines.stream()
                .map(orderLine -> orderLine.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderLine.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<Long, Product> getProductsFromOrderLines(List<OrderLineDTO> orderLineDTOList) {
        List<Long> productsIdsFromOrder = orderLineDTOList.stream()
                .map(OrderLineDTO::productId)
                .toList();

        List<Product> products = productRepository.findAllById(productsIdsFromOrder);

        return products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }
}
