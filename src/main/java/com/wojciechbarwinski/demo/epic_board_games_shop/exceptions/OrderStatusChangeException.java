package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;

public class OrderStatusChangeException extends ApplicationException {


    public OrderStatusChangeException(OrderStatus currentStatus, OrderStatus nextStatus) {
        super(generateMessage(currentStatus, nextStatus));
    }

    private static String generateMessage(OrderStatus currentStatus, OrderStatus nextStatus) {
        return String.format("Cannot transition from status '%s' to status '%s'. This transition is not allowed.",
                currentStatus, nextStatus);
    }
}
