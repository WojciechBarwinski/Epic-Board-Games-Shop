package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ApplicationException;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends ApplicationException {

    private final List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        super("Result of validation");
        this.errors = errors;
    }
}
