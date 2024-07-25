package com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions;

import lombok.Getter;

@Getter
public class InvalidAuthenticationException extends ApplicationSecurityException {

    public InvalidAuthenticationException() {
        super("JWT token has expired or is incorrect");
    }
}
