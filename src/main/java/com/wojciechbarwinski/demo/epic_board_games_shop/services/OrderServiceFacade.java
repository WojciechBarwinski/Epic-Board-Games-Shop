package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceFacade {

    private final OrderService orderService;

    public OrderServiceFacade(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderResponseDTO orderProceed(OrderRequestDTO orderRequestDTO) {
        return orderService.orderProceed(orderRequestDTO);
    }
}
