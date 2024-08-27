package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.OrderStatusChangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus.*;

@Slf4j
@Component
public class OrderStatusChangeValidation {

    private final Map<OrderStatus, List<OrderStatus>> allStatuses;

    public OrderStatusChangeValidation() {
        allStatuses = new HashMap<>();
        allStatuses.put(PLACED, List.of(CONFIRMED, CANCELLED));
        allStatuses.put(CONFIRMED, List.of(PAYMENT_VERIFICATION, CANCELLED));
        allStatuses.put(PAYMENT_VERIFICATION, List.of(PAID, CANCELLED));
        allStatuses.put(PAID, List.of(RECEIVED_BY_WAREHOUSE, CANCELLED));
        allStatuses.put(RECEIVED_BY_WAREHOUSE, List.of(COMPLETED, CANCELLED));
        allStatuses.put(COMPLETED, List.of(SHIPPED, CANCELLED));
        allStatuses.put(SHIPPED, List.of(DELIVERED));
        allStatuses.put(DELIVERED, List.of());
    }

    public void assertOrderStatusTransitionIsAllowed(OrderStatus currentStatus, OrderStatus nextStatus) {

        List<OrderStatus> orderStatuses = allStatuses.get(currentStatus);

        if (!orderStatuses.contains(nextStatus)) {
            log.warn("Unauthorized attempt to change order status: changing from {} to {} is not allowed.", currentStatus, nextStatus);
            throw new OrderStatusChangeException(currentStatus, nextStatus);
        }
    }
}
