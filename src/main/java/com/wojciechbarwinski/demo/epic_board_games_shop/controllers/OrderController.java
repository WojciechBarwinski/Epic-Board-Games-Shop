package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.SortDirection;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.OrderServiceFacade;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/orders")
    List<OrderResponseDTO> getOrdersBySearchingData(@RequestParam int page,
                                                    @RequestParam int size,
                                                    @RequestParam Map<String, String> filters,
                                                    @RequestParam Map<String, SortDirection> sorts) {

        throwExceptionIfPageOrSizeHasNegativeValue(page, size);

        return orderServiceFacade.getAllOrdersBySearchingData(new OrderSearchRequestDTO(page, size, filters, sorts));
    }

    private void throwExceptionIfPageOrSizeHasNegativeValue(int page, int size) {
        if (page < 0 || size < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page and size must be non-negative");
        }
    }
}
