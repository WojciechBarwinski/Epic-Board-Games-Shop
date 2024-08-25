package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRejectServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderHelper orderHelper;

    @InjectMocks
    private OrderRejectService orderRejectService;

    @Test
    void shouldSetOrderStatusOnCancelledAfterOrderWasRejected(){
        // given
        Long orderId = 1L;
        OrderStatus statusAfterCancelled = OrderStatus.CANCELLED;

        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(OrderStatus.PLACED);

        when(orderHelper.getOrderById(orderId)).thenReturn(order);

        //when
        orderRejectService.rejectOrder(orderId);

        // then
        verify(orderHelper).getOrderById(orderId);
        verify(orderRepository).save(order);
        assertEquals(statusAfterCancelled, order.getOrderStatus());
    }
}