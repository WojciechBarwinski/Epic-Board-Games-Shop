package com.wojciechbarwinski.demo.epic_board_games_shop.services.order;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.product.ProductServicesFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderStatusChangeValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class OrderRejectService {

    private final OrderRepository orderRepository;
    private final OrderHelper orderHelper;
    private final ProductServicesFacade productServicesFacade;
    private final OrderStatusChangeValidation orderStatusChangeValidation;


    void rejectOrder(Long id) {

        Order order = orderHelper.getOrderById(id);

        orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), OrderStatus.CANCELLED);
        order.setOrderStatus(OrderStatus.CANCELLED);
        log.info("Order with id {} was cancelled", order.getId());

        orderRepository.save(order);
        productServicesFacade.increaseProductQuantity(order.getOrderLines());
    }
}
