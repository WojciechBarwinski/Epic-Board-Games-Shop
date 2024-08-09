package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

public class MissingCredentialsException extends ApplicationException {

    public MissingCredentialsException() {
        super("Login data are null/blank");
    }
}
