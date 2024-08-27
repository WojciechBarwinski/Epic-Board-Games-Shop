package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

public class ProductDecreaseException extends ApplicationException {

    public ProductDecreaseException(Long productId, Integer quantityToRemove) {
        super(generateMessage(productId, quantityToRemove));
    }

    private static String generateMessage(Long productId, Integer quantityToRemove){
       return String.format("Cannot decrease quantity by '%d' for product with ID '%d'. Insufficient stock.",
                quantityToRemove, productId);
    }
}
