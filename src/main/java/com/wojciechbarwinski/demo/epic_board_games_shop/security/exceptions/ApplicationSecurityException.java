package com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions;

import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ApplicationException;

public class ApplicationSecurityException extends ApplicationException {

    public ApplicationSecurityException(String message) {
        super(message);
    }
}
