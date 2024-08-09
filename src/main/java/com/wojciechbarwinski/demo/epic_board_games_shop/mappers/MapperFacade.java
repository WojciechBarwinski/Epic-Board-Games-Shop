package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;


import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class MapperFacade {

    public Order mapCreateOrderRequestDTOToOrderEntity(CreateOrderRequestDTO createOrderRequestDto) {
        return OrderMapperTest.INSTANCE.mapCreateOrderRequestDTOToOrder(createOrderRequestDto);
    }

    public OrderResponseDTO mapOrderToOrderResponseDTO(Order order) {
        return OrderMapperTest.INSTANCE.mapOrderToResponseOrderDTO(order);
    }
}
