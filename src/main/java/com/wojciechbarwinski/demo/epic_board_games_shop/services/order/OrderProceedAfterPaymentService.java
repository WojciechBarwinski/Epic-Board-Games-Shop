package com.wojciechbarwinski.demo.epic_board_games_shop.services.order;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse.LegendaryWarehousePort;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderStatusChangeValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class OrderProceedAfterPaymentService {

    private final OrderHelper orderHelper;
    private final LegendaryWarehousePort port;
    private final OrderStatusChangeValidation orderStatusChangeValidation;

    void proceedOrderAfterPayment(String codeId) {
        Long id = Long.parseLong(codeId);// method to read codeId from link as order ID
        Order order = orderHelper.getOrderById(id);

        orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), OrderStatus.PAID);
        order.setOrderStatus(OrderStatus.PAID); //tmp useless
        log.info("Order with id {} was paid", order.getId());

        port.sendOrderToWarehouse(order);
    }
}
