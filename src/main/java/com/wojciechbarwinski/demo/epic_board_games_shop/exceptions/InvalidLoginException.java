package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import lombok.Getter;

@Getter
public class InvalidLoginException extends ApplicationUnauthorizedException {

    private final String message;

    public InvalidLoginException() {
        this.message = "Incorrect login and/or password";
    }
}
