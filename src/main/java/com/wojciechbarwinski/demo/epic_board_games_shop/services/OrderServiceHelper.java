package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.InsufficientStockException;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ProductsNotFoundException;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.ProductRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.AuthenticationComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderServiceHelper {

    private final ProductRepository productRepository;
    private final AuthenticationComponent authenticationComponent;

    Order prepareOrderToSave(CreateOrderRequestDTO orderDTO, Order order) {

        order.setOrderLines(getOrderLinesFromOrderDTO(orderDTO.getOrderLineDTOs(), order));
        order.setTotalPrice(getTotalOrderPrice(order.getOrderLines()));
        order.setEmployeeId(authenticationComponent.getSellerId());
        order.setOrderStatus(OrderStatus.PLACED);

        return order;
    }

    private List<OrderLine> getOrderLinesFromOrderDTO(List<OrderLineDTO> orderLineDTOs, Order order) {
        List<OrderLine> orderLines = new ArrayList<>();
        List<Long> missingProductsId = new ArrayList<>();
        List<Long> incorrectQuantityProductsId = new ArrayList<>();

        Map<Long, Product> productsFromDB = getProductsFromOrderLines(orderLineDTOs);

        for (OrderLineDTO orderLineDTO : orderLineDTOs) {
            Long productId = orderLineDTO.productId();
            Product productById = productsFromDB.get(productId);

            if (productById == null) {
                missingProductsId.add(orderLineDTO.productId());
            } else if (productById.getQuantity() <= orderLineDTO.quantity()) {
                incorrectQuantityProductsId.add(orderLineDTO.productId());
            } else {
                orderLines.add(OrderLine.builder()
                        .order(order)
                        .product(productById)
                        .quantity(orderLineDTO.quantity())
                        .build());
            }
        }

        checkAndThrowExceptionIfThereAreSomeIdInErrorsList(missingProductsId, incorrectQuantityProductsId);

        return orderLines;
    }

    private static void checkAndThrowExceptionIfThereAreSomeIdInErrorsList(List<Long> missingProductsId, List<Long> incorrectQuantityProductsId) {
        if (missingProductsId.size() > 0) {
            log.warn("There are some products in Order that aren't in DB.");
            throw new ProductsNotFoundException(missingProductsId);
        }
        if (incorrectQuantityProductsId.size() > 0) {
            log.warn("Some items are ordered in greater quantity then are in stock");
            throw new InsufficientStockException(incorrectQuantityProductsId);
        }
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
