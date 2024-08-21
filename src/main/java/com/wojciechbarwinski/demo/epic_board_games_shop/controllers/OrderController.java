package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.SortDirection;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.OrderServiceFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


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

    @GetMapping(value = "/{id}")
    OrderResponseDTO getOrderById(@PathVariable Long id) {

        return orderServiceFacade.getOrderById(id);
    }

    @GetMapping("/orders")
    List<OrderResponseDTO> getOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "5") int size,
                                     @RequestParam(value = "sortField", required = false) String sortField,
                                     @RequestParam(value = "sortDirection", required = false) SortDirection sortDirection,
                                     @RequestParam(value = "ordererMail", required = false) List<String> ordererMail,
                                     @RequestParam(value = "status", required = false) List<String> status,
                                     @RequestParam(value = "phone", required = false) List<String> phone,
                                     @RequestParam(value = "city", required = false) List<String> city,
                                     @RequestParam(value = "street", required = false) List<String> street,
                                     @RequestParam(value = "sellerMail", required = false) List<String> sellerMail) {

        throwExceptionIfPageOrSizeHasNegativeValue(page, size);

        OrderSearchRequestDTO build = OrderSearchRequestDTO.builder()
                .page(page)
                .size(size)
                .sortField(sortField)
                .sortDirection(sortDirection)
                .ordererMail(ordererMail)
                .status(status)
                .phone(phone)
                .city(city)
                .street(street)
                .sellerMail(sellerMail)
                .build();

        return orderServiceFacade.getAllOrdersBySearchingData(build);
    }

    private void throwExceptionIfPageOrSizeHasNegativeValue(int page, int size) {
        if (page < 0 || size < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page and size must be non-negative");
        }
    }
}

