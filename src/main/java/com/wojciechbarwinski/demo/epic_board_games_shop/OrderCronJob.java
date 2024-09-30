package com.wojciechbarwinski.demo.epic_board_games_shop;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse.LegendaryWarehousePort;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
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

    @Value("${orders.cleanup.not-paid.timer-minutes}")
    private int notPayedTimer;

    private final OrderRepository orderRepository;
    private final LegendaryWarehousePort legendaryWarehousePort;


    @Scheduled(cron = "${orders.cleanup.not-confirmed.cronexpr}")
    public void cancelOrdersNotConfirmedForTooLong() {

        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(notConfirmedTimer);
        List<Order> orders = orderRepository.findByOrderStatusAndStatusUpdatedAtBefore(OrderStatus.PLACED, thresholdTime);

        for (Order order : orders) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        }
    }

    @Scheduled(cron = "${orders.cleanup.not-paid.cronexpr}")
    public void cancelOrdersNotPaidForTooLong() {

        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(notPayedTimer);
        List<Order> orders = orderRepository.findByOrderStatusAndStatusUpdatedAtBefore(OrderStatus.PAYMENT_VERIFICATION, thresholdTime);

        for (Order order : orders) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        }
    }

    @Scheduled(cron = "${orders.cleanup.send-paid.cronexpr}")
    public void sendOrdersThatArePaid() {

        List<Order> orders = orderRepository.findByOrderStatus(OrderStatus.PAID);

        for (Order order : orders) {
            legendaryWarehousePort.sendOrderToWarehouse(order);
        }
    }

}
