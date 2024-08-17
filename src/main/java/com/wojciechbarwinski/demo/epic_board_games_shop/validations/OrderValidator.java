package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderValidator {

    public void validateOrderLineDTOS(List<OrderLineDTO> orderLineDTOs, Map<Long, Product> productsFromDB) {
        List<ValidationError> errors = new ArrayList<>();

        for (OrderLineDTO orderLineDTO : orderLineDTOs) {
            validateOrderLineDTO(productsFromDB, orderLineDTO, errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateOrderLineDTO(Map<Long, Product> productsFromDB, OrderLineDTO orderLineDTO, List<ValidationError> errors) {
        Long productId = orderLineDTO.productId();
        Product productById = productsFromDB.get(productId);

        if (productById == null) {
            errors.add(new ValidationError(
                    "orderLineDTO.productId",
                    "There is no product with this id",
                    orderLineDTO.productId()));
        } else if (productById.getQuantity() <= orderLineDTO.quantity()) {
            errors.add(new ValidationError(
                    "orderLineDTO.quantity",
                    String.format("There is insufficient quantity for product with id %d", productId),
                    new OrderLineQuantityValidationRejectedValueDTO(orderLineDTO.quantity(), productById.getQuantity())));
        }
    }
}
