package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.OrderNotFoundException;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderHelperTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    OrderHelper orderHelper;

    @Test
    void shouldThrowExceptionWhenOrderIsNotFound() {
        // given
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when & then
        OrderNotFoundException orderNotFoundException = assertThrows(OrderNotFoundException.class, () ->
                orderHelper.getOrderById(orderId));

        verify(orderRepository).findById(orderId);
        assertEquals("There is no Order with id: 1", orderNotFoundException.getMessage());
    }

}