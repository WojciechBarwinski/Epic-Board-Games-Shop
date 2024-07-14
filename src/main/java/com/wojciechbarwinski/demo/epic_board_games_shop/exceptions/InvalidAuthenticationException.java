package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import lombok.Getter;

@Getter
public class InvalidAuthenticationException extends ApplicationUnauthorizedException {

    private final String message;

    public InvalidAuthenticationException() {
        this.message = "JWT token has expired or is incorrect.";
    }
}
