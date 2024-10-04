package com.wojciechbarwinski.demo.epic_board_games_shop.services.order;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderStatusChangeValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class OrderUpdateStatusFromWarehouseService {

    private final OrderRepository orderRepository;
    private final OrderHelper orderHelper;
    private final OrderStatusChangeValidation orderStatusChangeValidation;

    void updateStatusFromWarehouse(OrderDataFromWarehouseDTO orderDataDTO) {
        Order order = orderHelper.getOrderById(orderDataDTO.orderId());

        orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), orderDataDTO.status());
        order.setOrderStatus(orderDataDTO.status());
        log.info("Status for Order with id {} was update from warehouse to {}", order.getId(), orderDataDTO.status());
        orderRepository.save(order);
    }
}
