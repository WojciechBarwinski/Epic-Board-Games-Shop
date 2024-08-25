package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUpdateStatusFromWarehouseServiceTest {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderHelper orderHelper;

    @InjectMocks
    private OrderUpdateStatusFromWarehouseService orderUpdateStatusFromWarehouseService;

    @Test
    void shouldUpdateOrderStatusWhenOrderExists() {
        // given
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.DELIVERED;
        OrderDataFromWarehouseDTO orderDataDTO = new OrderDataFromWarehouseDTO(orderId, newStatus);

        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(OrderStatus.PLACED);

        when(orderHelper.getOrderById(orderId)).thenReturn(order);

        // when
        orderUpdateStatusFromWarehouseService.updateStatusFromWarehouse(orderDataDTO);

        // then
        verify(orderHelper).getOrderById(orderId);
        verify(orderRepository).save(order);
        assertEquals(newStatus, order.getOrderStatus());
    }


}