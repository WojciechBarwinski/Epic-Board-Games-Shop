package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderLineMapper {

    OrderLineMapper INSTANCE = Mappers.getMapper(OrderLineMapper.class);

    @Mapping(source = "product.id", target = "productId")
    OrderLineDTO orderLineToOrderLineDTO(OrderLine orderLine);

}
