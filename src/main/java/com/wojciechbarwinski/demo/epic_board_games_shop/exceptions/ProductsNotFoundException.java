package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class ProductsNotFoundException extends ApplicationException {

    public ProductsNotFoundException(Collection<Long> missingProductsId) {
        super(generateMessage(missingProductsId));
    }

    private static String generateMessage(Collection<Long> missingProductsId) {
        return "There are missing products with id = " +
                missingProductsId.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
    }
}
