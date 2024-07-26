package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final MapperFacade mapper;
    private final OrderServiceHelper orderHelper;

    OrderResponseDTO processOrder(OrderRequestDTO orderRequestDTO) {

        Order order = orderHelper.prepareOrderToSave(orderRequestDTO,
                mapper.mapOrderRequestDTOToOrderEntity(orderRequestDTO));

        Order orderAfterSave = orderRepository.save(order);

        return mapper.mapOrderToOrderResponseDTO(orderAfterSave);
    }

}
