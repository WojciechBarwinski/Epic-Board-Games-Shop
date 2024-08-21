package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.ProductDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);
}
