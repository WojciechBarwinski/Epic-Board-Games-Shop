package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.OrderStatusChangeException;
import org.junit.jupiter.api.Test;

import static com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderStatusChangeValidationTest {

    private final OrderStatusChangeValidation statusValidation = new OrderStatusChangeValidation();

    @Test
    void shouldReturnCANCELLEDStatus() {
        OrderStatus currentStatus = PAID;
        OrderStatus nextStatus = CANCELLED;

        OrderStatus newStatus = statusValidation.setValidatedOrderStatus(currentStatus, nextStatus);

        assertEquals(nextStatus, newStatus);
    }

    @Test
    void shouldReturnON_HOLDStatusWhenCurrentStatusIsAnotherThenPLACED() {
        OrderStatus currentStatus = PAID;
        OrderStatus nextStatus = ON_HOLD;

        OrderStatus newStatus = statusValidation.setValidatedOrderStatus(currentStatus, nextStatus);

        assertEquals(nextStatus, newStatus);
    }

    @Test
    void shouldReturnRECEIVED_BY_WAREHOUSEStatusWhenCurrentStatusIsPAID() {
        OrderStatus currentStatus = PAID;
        OrderStatus nextStatus = RECEIVED_BY_WAREHOUSE;

        OrderStatus newStatus = statusValidation.setValidatedOrderStatus(currentStatus, nextStatus);

        assertEquals(nextStatus, newStatus);
    }

    @Test
    void shouldReturnDELIVEREDStatusWhenCurrentStatusIsSHIPPED() {
        OrderStatus currentStatus = SHIPPED;
        OrderStatus nextStatus = DELIVERED;

        OrderStatus newStatus = statusValidation.setValidatedOrderStatus(currentStatus, nextStatus);

        assertEquals(nextStatus, newStatus);
    }

    @Test
    void shouldThrowExceptionWhenNextStatusIsON_HOLDAndCurrentStatusIsPLACED() {
        OrderStatus currentStatus = PLACED;
        OrderStatus nextStatus = ON_HOLD;
        String exceptionMessage = "Cannot transition from status 'PLACED' to status 'ON_HOLD'. This transition is not allowed.";

        OrderStatusChangeException exception = assertThrows(OrderStatusChangeException.class,
                () -> statusValidation.setValidatedOrderStatus(currentStatus, nextStatus));

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNextStatusIsSHIPPEDAndCurrentStatusIsPAID() {
        OrderStatus currentStatus = PAID;
        OrderStatus nextStatus = SHIPPED;
        String exceptionMessage = "Cannot transition from status 'PAID' to status 'SHIPPED'. This transition is not allowed.";

        OrderStatusChangeException exception = assertThrows(OrderStatusChangeException.class,
                () -> statusValidation.setValidatedOrderStatus(currentStatus, nextStatus));

        assertEquals(exceptionMessage, exception.getMessage());
    }

}