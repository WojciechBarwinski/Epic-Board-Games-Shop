package com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions;

import lombok.Getter;

@Getter
public class InvalidLoginException extends ApplicationSecurityException {

    public InvalidLoginException() {
        super("Incorrect login or password");
    }
}
