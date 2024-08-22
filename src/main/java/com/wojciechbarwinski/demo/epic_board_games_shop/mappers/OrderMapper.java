package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataToWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressMapper.class, OrderLineMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "addressToSend", target = "address")
    Order mapCreateOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO);


    @Mapping(source = "address", target = "addressToSend")
    @Mapping(source = "employeeId", target = "sellerId")
    @Mapping(source = "orderStatus", target = "status")
    @Mapping(source = "orderLines", target = "orderLineDTOS")
    OrderResponseDTO mapOrderToResponseOrderDTO(Order order);

    @Mapping(source = "address", target = "addressDTO")
    @Mapping(source = "orderLines", target = "orderLineDTOS")
    OrderDataToWarehouseDTO mapOrderToOrderDataToWarehouseDTO(Order order);

}
