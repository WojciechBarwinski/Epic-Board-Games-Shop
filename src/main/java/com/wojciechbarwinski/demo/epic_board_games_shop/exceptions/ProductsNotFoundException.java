package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductsNotFoundException extends ApplicationException{

    private final List<Long> missingProductsId;
    private final String  message;

    public ProductsNotFoundException(List<Long> missingProductsId) {
        this.missingProductsId = missingProductsId;
        this.message = generateMessage(missingProductsId);
    }

    private String generateMessage(List<Long> missingProductsId) {
        return "We can't find products with id = " +
                missingProductsId.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
    }
}
