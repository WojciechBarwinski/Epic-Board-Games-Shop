package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;


import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ErrorResponse;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.MappingToDTOException;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.MissingCredentialsException;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ProductsNotFoundException;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions.ApplicationSecurityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AppExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductsNotFoundException.class)
    public ErrorResponse<String> productsNotFoundException(ProductsNotFoundException exception) {

        return new ErrorResponse<>(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ApplicationSecurityException.class)
    public ErrorResponse<String> applicationSecurityException(ApplicationSecurityException exception) {

        return new ErrorResponse<>(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingCredentialsException.class)
    public ErrorResponse<String> missingCredentialsException(MissingCredentialsException exception) {

        return new ErrorResponse<>(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MappingToDTOException.class)
    public ErrorResponse<String> mappingToDTOException(MappingToDTOException exception) {

        return new ErrorResponse<>(exception.getMessage());
    }
}
