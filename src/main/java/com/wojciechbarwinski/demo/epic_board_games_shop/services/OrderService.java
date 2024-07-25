package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ProductsNotFoundException;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.ProductRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions.InvalidSellerException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MapperFacade mapper;


    OrderResponseDTO processOrder(OrderRequestDTO orderRequestDTO) {

        Order order = mapper.mapOrderDTOToOrderEntity(orderRequestDTO);

        order.setOrderLines(getOrderLinesFromDTO(orderRequestDTO.getOrderLineDTOList(), order));
        order.setTotalPrice(getTotalOrderPrice(order.getOrderLines()));
        order.setEmployeeId(getSellerId());

        Order orderAfterSave = orderRepository.save(order);

        return mapper.mapOrderToOrderResponseDTO(orderAfterSave);

    }

    private List<OrderLine> getOrderLinesFromDTO(List<OrderLineDTO> orderLineDTOList, Order order) {
        List<OrderLine> orderLines = new ArrayList<>();
        List<Long> missingProductsId = new ArrayList<>();

        Map<Long, Product> productsFromDB = getProductsFromOrderLines(orderLineDTOList);

        for (OrderLineDTO orderLineDTO : orderLineDTOList) {
            Long productId = orderLineDTO.productId();
            Product productById = productsFromDB.get(productId);

            if (productById == null) {
                missingProductsId.add(orderLineDTO.productId());
            } else {
                orderLines.add(OrderLine.builder()
                        .order(order)
                        .product(productById)
                        .quantity(orderLineDTO.quantity())
                        .build());
            }
        }

        if (!missingProductsId.isEmpty()) {
            throw new ProductsNotFoundException(missingProductsId);
        }

        return orderLines;
    }

    private BigDecimal getTotalOrderPrice(List<OrderLine> orderLines) {
        BigDecimal totalPrice = new BigDecimal("0");

        for (OrderLine orderLine : orderLines) {
            int quantity = orderLine.getQuantity();
            BigDecimal pricePerUnit = orderLine.getProduct().getPrice();

            BigDecimal linePrice = pricePerUnit.multiply(BigDecimal.valueOf(quantity));

            totalPrice = totalPrice.add(linePrice);
        }

        return totalPrice;
    }

    private String getSellerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }
        throw new InvalidSellerException();
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
