package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

public class OrderNotFoundException extends ApplicationException{


    public OrderNotFoundException(String message) {
        super("There is no Order with id: " + message);
    }
}
