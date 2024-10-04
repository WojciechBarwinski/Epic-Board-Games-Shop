package com.wojciechbarwinski.demo.epic_board_games_shop.services.order;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import com.wojciechbarwinski.demo.epic_board_games_shop.validations.OrderStatusChangeValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderProceedAfterConfirmServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderHelper orderHelper;

    @Mock
    private MapperFacade mapperFacade;

    @Mock
    private OrderStatusChangeValidation orderStatusChangeValidation;

    @InjectMocks
    private OrderProceedAfterConfirmService orderProceedAfterConfirmService;

    @Test
    void shouldSerOrderStatusOnConfirmAfterOrderConfirm() {
        // given
        Long orderId = 1L;
        OrderStatus statusAfterConfirm = OrderStatus.CONFIRMED;

        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(OrderStatus.PLACED);

        when(orderHelper.getOrderById(orderId)).thenReturn(order);
        when(mapperFacade.mapOrderToOrderResponseDTO(order)).thenReturn(null);

        // when
        orderProceedAfterConfirmService.proceedOrderAfterConfirm(orderId);

        // then
        verify(orderHelper).getOrderById(orderId);
        verify(orderRepository).save(order);
        assertEquals(statusAfterConfirm, order.getOrderStatus());
    }
}