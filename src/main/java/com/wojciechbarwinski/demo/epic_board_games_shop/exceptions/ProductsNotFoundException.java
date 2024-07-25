package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class ProductsNotFoundException extends ApplicationException {

    private final Collection<Long> missingProductsId;


    public ProductsNotFoundException(Collection<Long> missingProductsId) {
        super(generateMessage(missingProductsId));
        this.missingProductsId = missingProductsId;
    }

    private static String generateMessage(Collection<Long> missingProductsId) {
        return "We can't find products with id = " +
                missingProductsId.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
    }
}
