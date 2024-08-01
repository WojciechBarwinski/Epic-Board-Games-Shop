package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;


import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MapperFacade {

    private final OrderMapper orderMapper;

    public Order mapOrderRequestDTOToOrderEntity(CreateOrderRequestDTO createOrderRequestDto){
        return orderMapper.mapOrderRequestDTOToOrderEntity(createOrderRequestDto);
    }

    public OrderResponseDTO mapOrderToOrderResponseDTO(Order order){
        return orderMapper.mapOrderToOrderResponseDTO(order);
    }
}
