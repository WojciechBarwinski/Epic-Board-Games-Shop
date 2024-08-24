package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.OrderStatusChangeException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus.*;

@Component
public class OrderStatusChangeValidation {

    private final List<OrderStatus> allStatuses = Arrays.asList(OrderStatus.values());

    public OrderStatus setValidatedOrderStatus(OrderStatus currentStatus, OrderStatus nextStatus) {

        if (nextStatus == CANCELLED || (nextStatus == ON_HOLD && currentStatus != PLACED)) { //There should not be option to HOLD order which was not CONFIRMED
            return nextStatus;
        }

        int currentIndex = allStatuses.indexOf(currentStatus);
        int nextIndex = allStatuses.indexOf(nextStatus);

        if (nextIndex - currentIndex == 1) {
            return nextStatus;
        }


        throw new OrderStatusChangeException(currentStatus, nextStatus);
    }
}
