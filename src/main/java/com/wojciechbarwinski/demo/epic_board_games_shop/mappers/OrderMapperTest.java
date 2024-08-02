package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressMapper.class, OrderLineMapper.class})
public interface OrderMapperTest {

    OrderMapperTest INSTANCE = Mappers.getMapper(OrderMapperTest.class);

    @Mapping(source = "addressToSend", target = "address")
    Order mapCreateOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO);


    @Mapping(source = "address", target = "addressToSend")
    @Mapping(source = "employeeId", target = "sellerId")
    @Mapping(source = "orderStatus", target = "status")
    @Mapping(source = "orderLines", target = "orderLineDTOs")
    OrderResponseDTO mapOrderToResponseOrderDTO(Order order);

}
