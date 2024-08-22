package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class OrderCreateService {

    private final OrderRepository orderRepository;
    private final MapperFacade mapper;
    private final OrderCreateServiceHelper orderHelper;

    OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDto) {

        Order order = orderHelper.prepareOrderToSave(createOrderRequestDto,
                mapper.mapCreateOrderRequestDTOToOrderEntity(createOrderRequestDto));

        Order orderAfterSave = orderRepository.save(order);

        log.info("Order with id {} was created", orderAfterSave.getId());
        return mapper.mapOrderToOrderResponseDTO(orderAfterSave);
    }
}
