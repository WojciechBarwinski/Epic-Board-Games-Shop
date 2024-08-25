package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.OrderStatusChangeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderStatusChangeValidationTest {

    private final OrderStatusChangeValidation statusValidation = new OrderStatusChangeValidation();

    @ParameterizedTest
    @MethodSource("getCorrectSetOfStatus")
    void shouldNotThrowExceptionWhenStatusChangeAreCorrect(OrderStatusTransitionToTest orderStatusSet) {
        //when/then
        statusValidation.assertOrderStatusTransitionIsAllowed(orderStatusSet.currentStatus, orderStatusSet.nextStatus);
    }

    @ParameterizedTest
    @MethodSource("getWrongSetOfStatus")
    void shouldThrowExceptionWhenStatusChangeAreIncorrect(OrderStatusTransitionToTest orderStatusSet) {
        //when/then
        assertThrows(OrderStatusChangeException.class,
                () -> statusValidation.assertOrderStatusTransitionIsAllowed(orderStatusSet.currentStatus, orderStatusSet.nextStatus));
    }


    private static Stream<OrderStatusTransitionToTest> getCorrectSetOfStatus() {
        return Stream.of(
                new OrderStatusTransitionToTest(PAID, CANCELLED),
                new OrderStatusTransitionToTest(PAID, ON_HOLD),
                new OrderStatusTransitionToTest(PAID, RECEIVED_BY_WAREHOUSE),
                new OrderStatusTransitionToTest(SHIPPED, DELIVERED)
        );
    }

    private static Stream<OrderStatusTransitionToTest> getWrongSetOfStatus() {
        return Stream.of(
                new OrderStatusTransitionToTest(PAID, SHIPPED),
                new OrderStatusTransitionToTest(PLACED, ON_HOLD),
                new OrderStatusTransitionToTest(PAID, COMPLETED)
        );
    }


    private record OrderStatusTransitionToTest(
            OrderStatus currentStatus,
            OrderStatus nextStatus) {
    }
}