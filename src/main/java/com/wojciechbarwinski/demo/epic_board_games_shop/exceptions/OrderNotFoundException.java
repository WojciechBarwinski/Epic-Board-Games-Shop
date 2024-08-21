package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

public class OrderNotFoundException extends ApplicationException{


    public OrderNotFoundException(Long id) {
        super("There is no Order with id: " + id);
    }
}
