package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse<T> {

    private String errorMessage;
    private T details;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
