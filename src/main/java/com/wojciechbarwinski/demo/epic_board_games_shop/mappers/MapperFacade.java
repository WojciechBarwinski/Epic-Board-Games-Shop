package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;


import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MapperFacade {

    private final OrderMapper orderMapper;

    public Order mapOrderDTOToOrderEntity(OrderRequestDTO orderRequestDTO){
        return orderMapper.mapOrderDTOToOrderEntity(orderRequestDTO);
    }

    public OrderResponseDTO mapOrderToOrderResponseDTO(Order order){
        return orderMapper.mapOrderToOrderResponseDTO(order);
    }
}
