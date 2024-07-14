package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;


import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ApplicationUnauthorizedException;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ErrorResponse;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ProductsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class AppExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(ProductsNotFoundException.class)
    public ErrorResponse<String> missingProductException(ProductsNotFoundException exception) {

        return new ErrorResponse<>(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ApplicationUnauthorizedException.class)
    public ErrorResponse<String> LoginException(ApplicationUnauthorizedException exception) {

        return new ErrorResponse<>(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse<String> usernameNotFoundException(UsernameNotFoundException exception) {

        return new ErrorResponse<>(exception.getMessage());
    }
}
