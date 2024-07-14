package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;


import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class MapperFacade {

    private final OrderMapper orderMapper;

    public MapperFacade() {
        this.orderMapper = new OrderMapper();
    }

    public Order mapOrderDTOToOrderEntity(OrderRequestDTO orderRequestDTO){
        return orderMapper.mapOrderDTOToOrderEntity(orderRequestDTO);
    }

    public OrderResponseDTO mapOrderToOrderResponseDTO(Order order){
        return orderMapper.mapOrderToOrderResponseDTO(order);
    }
}
