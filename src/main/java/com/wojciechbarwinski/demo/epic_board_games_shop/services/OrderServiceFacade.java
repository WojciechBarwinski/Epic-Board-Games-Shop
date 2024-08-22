package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceFacade {

    private final OrderUpdateStatusFromWarehouseService orderUpdateStatusFromWarehouseService;
    private final OrderCreateService orderCreateService;
    private final OrderReadService orderReadService;
    private final OrderRejectService orderRejectService;
    private final OrderCancelService orderCancelService;
    private final OrderProceedAfterConfirmService orderProceedAfterConfirmService;
    private final OrderProceedAfterPaymentService orderProceedAfterPaymentService;

    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDto) {
        return orderCreateService.createOrder(createOrderRequestDto);
    }

    public OrderResponseDTO getOrderById(Long id) {
        return orderReadService.getOrderById(id);
    }

    public List<OrderResponseDTO> getAllOrdersBySearchingData(OrderSearchRequestDTO orderSearchRequestDTO) {
        return orderReadService.getAllOrdersBySearchingData(orderSearchRequestDTO);
    }

    public OrderResponseDTO proceedOrderAfterConfirm(Long id) {
        return orderProceedAfterConfirmService.proceedOrderAfterConfirm(id);
    }

    public void rejectOrder(Long id) {
        orderRejectService.rejectOrder(id);
    }

    public void updateStatusFromWarehouse(OrderDataFromWarehouseDTO orderDataFromWarehouseDTO) {
        orderUpdateStatusFromWarehouseService.updateStatusFromWarehouse(orderDataFromWarehouseDTO);
    }

    public void cancelOrder(String codeId) {
        orderCancelService.cancelOrder(codeId);
    }

    public void proceedOrderAfterPayment(String codeId) {
        orderProceedAfterPaymentService.proceedOrderAfterPayment(codeId);
    }
}
