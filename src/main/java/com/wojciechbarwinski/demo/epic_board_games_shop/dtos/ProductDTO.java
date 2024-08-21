package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String name,
        BigDecimal price,
        int quantity) {

}
