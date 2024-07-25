package com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions;

import lombok.Getter;

@Getter
public class InvalidSellerException extends ApplicationSecurityException {

    public InvalidSellerException() {
        super("No authenticated user found.");
    }
}
