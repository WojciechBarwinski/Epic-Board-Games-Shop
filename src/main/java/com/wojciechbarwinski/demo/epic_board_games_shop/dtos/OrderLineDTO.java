package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineDTO(

        @NotNull(message = "Product ID is required")
        @Positive(message = "Product ID must be a positive number")
        Long productId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity) {
}
