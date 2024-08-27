package com.wojciechbarwinski.demo.epic_board_games_shop.services.product;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.ProductDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProductReadService {

    private final ProductRepository productRepository;
    private final MapperFacade mapper;

    List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(mapper::mapProductToProductDTO)
                .toList();
    }
}

