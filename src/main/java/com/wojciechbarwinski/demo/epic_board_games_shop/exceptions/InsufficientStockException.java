package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

public class InsufficientStockException extends ApplicationException {


    public InsufficientStockException(Collection<Long> incorrectQuantityProductsId) {
        super(generateMessage(incorrectQuantityProductsId));
    }

    private static String generateMessage(Collection<Long> missingProductsId) {
        return "There is incorrect data about stock of this items = " +
                missingProductsId.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
    }
}
