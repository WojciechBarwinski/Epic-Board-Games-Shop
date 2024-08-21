package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceFacade {

    private final OrderService orderService;

    public OrderResponseDTO orderProceed(CreateOrderRequestDTO createOrderRequestDto) {
        return orderService.processOrder(createOrderRequestDto);
    }


    public OrderResponseDTO getOrderById(Long id) {
        return orderService.getOrderById(id);

    public List<OrderResponseDTO> getAllOrdersBySearchingData(OrderSearchRequestDTO orderSearchRequestDTO) {
        return orderService.getAllOrdersBySearchingData(orderSearchRequestDTO);

    }
}
