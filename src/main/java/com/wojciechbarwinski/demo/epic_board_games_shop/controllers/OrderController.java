package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequest;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.OrderServiceFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceFacade orderServiceFacade;

    @PostMapping
    OrderResponseDTO createOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDto) {

        log.debug("Start proceed order for '{}'", createOrderRequestDto.getOrdererMail());

        return orderServiceFacade.orderProceed(createOrderRequestDto);
    }

    @PostMapping("/orders")
    List<OrderResponseDTO> getOrdersBySearchingData(@RequestBody OrderSearchRequest orderSearchRequest) {

        return orderServiceFacade.getAllOrdersBySearchingData(orderSearchRequest);
    }

}
