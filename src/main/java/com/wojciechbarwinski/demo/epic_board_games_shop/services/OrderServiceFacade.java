package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequest;
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

    public List<OrderResponseDTO> getAllOrdersBySearchingData(OrderSearchRequest orderSearchRequest) {
        return orderService.getAllOrdersBySearchingData(orderSearchRequest);
    }
}
