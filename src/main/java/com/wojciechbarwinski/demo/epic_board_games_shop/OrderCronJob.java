package com.wojciechbarwinski.demo.epic_board_games_shop;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCronJob {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 */10 * * * * ") //every 10 minutes
    public void cancelOrderAfterTenMinutesOnPLACEStage() {

        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(10);
        List<Order> orders = orderRepository.findByOrderStatusAndActualStatusSetDateBefore(OrderStatus.PLACED, thresholdTime);

        for (Order order : orders) {
            order.setOrderStatus(OrderStatus.CANCELLED);
        }

        orderRepository.saveAll(orders);
    }

    @Scheduled(cron = "0 0 * * * *") //every 24H
    public void cancelOrderAfter24HoursOnPAYMENT_VERIFICATIONStage() {

        LocalDateTime thresholdTime = LocalDateTime.now().minusHours(24);
        List<Order> orders = orderRepository.findByOrderStatusAndActualStatusSetDateBefore(OrderStatus.PAYMENT_VERIFICATION, thresholdTime);

        for (Order order : orders) {
            order.setOrderStatus(OrderStatus.CANCELLED);
        }

        orderRepository.saveAll(orders);
    }

    @Scheduled(cron = "0 0 14 * * *") // every day at 14:00
    public void sendAllPaidOrdersToWarehouse(){
        List<Order> byOrderStatus = orderRepository.findByOrderStatus(OrderStatus.PAID);

        //TODO after marge with ES-010 Order Flow
    }
}
