package com.wojciechbarwinski.demo.epic_board_games_shop.services.order;

import com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse.LegendaryWarehousePort;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataToWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderStatusChangeValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class OrderProceedAfterPaymentService {

    private final OrderRepository orderRepository;
    private final MapperFacade mapper;
    private final OrderHelper orderHelper;
    private final LegendaryWarehousePort port;
    private final OrderStatusChangeValidation orderStatusChangeValidation;

    void proceedOrderAfterPayment(String codeId) {
        Long id = Long.parseLong(codeId);// method to read codeId from link as order ID
        Order order = orderHelper.getOrderById(id);

        orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), OrderStatus.PAID);
        order.setOrderStatus(OrderStatus.PAID); //tmp useless
        log.info("Order with id {} was paid", order.getId());

        OrderDataToWarehouseDTO orderDataToWarehouseDTO = mapper.mapOrderToOrderDataToWarehouse(order);
        log.info("Order with id {} is sending to warehouse...", order.getId());

        try {
            OrderDataFromWarehouseDTO orderDataFromWarehouseDTO = port.sendOrderToWarehouse(orderDataToWarehouseDTO);
            orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), orderDataFromWarehouseDTO.status());
            order.setOrderStatus(orderDataFromWarehouseDTO.status());
        } catch (Exception e) {
            log.warn("Order with id {} was NOT sending to warehouse successfully", order.getId(), e);
        }

        log.info("Order with id {} was sending to warehouse successfully", order.getId());

        orderRepository.save(order);
    }
}
