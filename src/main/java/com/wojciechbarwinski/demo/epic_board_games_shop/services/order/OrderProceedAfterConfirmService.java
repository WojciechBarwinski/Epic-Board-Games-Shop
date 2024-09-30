package com.wojciechbarwinski.demo.epic_board_games_shop.services.order;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
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
class OrderProceedAfterConfirmService {

    private final OrderRepository orderRepository;
    private final MapperFacade mapper;
    private final OrderHelper orderHelper;
    private final OrderStatusChangeValidation orderStatusChangeValidation;

    OrderResponseDTO proceedOrderAfterConfirm(Long id) {
        Order order = orderHelper.getOrderById(id);

        orderStatusChangeValidation.assertOrderStatusTransitionIsAllowed(order.getOrderStatus(), OrderStatus.CONFIRMED);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        log.info("Order with id {} was confirm", order.getId());
        //here will be method that create payment-link and sent it to client mail to pay OR to decline order
        orderRepository.save(order);
        return mapper.mapOrderToOrderResponseDTO(order);
    }
}
