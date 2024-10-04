package com.wojciechbarwinski.demo.epic_board_games_shop.services.order;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.product.ProductServicesFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderStatusChangeValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCancelServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderHelper orderHelper;

    @Mock
    private ProductServicesFacade productServicesFacade;

    @Mock
    private OrderStatusChangeValidation orderStatusChangeValidation;

    @InjectMocks
    private OrderCancelService orderCancelService;

    @Test
    void shouldSetOrderStatusOnCancelledAfterOrderCancelled(){
        // given
        String codeId = "1";
        Long orderId = 1L;
        OrderStatus statusAfterCancelled = OrderStatus.CANCELLED;

        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(OrderStatus.PLACED);

        when(orderHelper.getOrderById(orderId)).thenReturn(order);

        //when
        orderCancelService.cancelOrder(codeId);

        // then
        verify(orderHelper).getOrderById(orderId);
        verify(orderRepository).save(order);
        assertEquals(statusAfterCancelled, order.getOrderStatus());
    }

}