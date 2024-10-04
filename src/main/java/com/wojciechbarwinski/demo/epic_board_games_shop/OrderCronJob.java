package com.wojciechbarwinski.demo.epic_board_games_shop;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.product.ProductServicesFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderStatusChangeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCronJob {

    @Value("${orders.cleanup.not-confirmed.timer-minutes}")
    private int notConfirmedTimer;

    @Value("${orders.cleanup.not-payed.timer-minutes}")
    private int notPayedTimer;

    private final OrderRepository orderRepository;
    private final ProductServicesFacade productServicesFacade;
    private final OrderStatusChangeValidation orderStatusChangeValidation;


    @Scheduled(cron = "${orders.cleanup.not-confirmed.cronexpr}")
    public void cancelOrdersNotConfirmedForTooLong() {

        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(notConfirmedTimer);
        List<Order> orders = orderRepository.findByOrderStatusAndStatusUpdatedAtBefore(OrderStatus.PLACED, thresholdTime);

        for (Order order : orders) {
            orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), OrderStatus.CANCELLED);
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            productServicesFacade.increaseProductQuantity(order.getOrderLines());
        }
    }

    @Scheduled(cron = "${orders.cleanup.not-payed.cronexpr}")
    public void cancelOrdersNotPaidForTooLong() {

        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(notPayedTimer);
        List<Order> orders = orderRepository.findByOrderStatusAndStatusUpdatedAtBefore(OrderStatus.PAYMENT_VERIFICATION, thresholdTime);

        for (Order order : orders) {
            orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), OrderStatus.CANCELLED);
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            productServicesFacade.increaseProductQuantity(order.getOrderLines());
        }
    }

}
