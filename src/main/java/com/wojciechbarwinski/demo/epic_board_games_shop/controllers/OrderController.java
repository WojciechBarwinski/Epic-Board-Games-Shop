package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.OrderServiceFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceFacade orderServiceFacade;

    @PostMapping
    OrderResponseDTO createOrder(@Valid @RequestBody CreateOrderRequestDTO createOrderRequestDto) {

        return orderServiceFacade.orderProceed(createOrderRequestDto);
    }
}
