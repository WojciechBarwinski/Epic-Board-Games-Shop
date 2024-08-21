package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {
    private String fieldName;
    private String message;
    private Object rejectedValue;
}
