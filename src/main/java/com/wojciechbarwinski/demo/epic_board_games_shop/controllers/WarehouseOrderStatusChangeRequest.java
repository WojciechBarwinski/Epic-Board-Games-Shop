package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WarehouseOrderStatusChangeRequest(

        @Positive(message = "OrderId must be positive number")
        Long orderId,

        @NotNull(message = "Status is mandatory")
        OrderStatus status
) {
}