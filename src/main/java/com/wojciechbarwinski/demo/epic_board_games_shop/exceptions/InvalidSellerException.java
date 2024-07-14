package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import lombok.Getter;

@Getter
public class InvalidSellerException extends ApplicationUnauthorizedException {

    private final String message;

    public InvalidSellerException() {
        this.message = "No authenticated user found.";
    }
}
