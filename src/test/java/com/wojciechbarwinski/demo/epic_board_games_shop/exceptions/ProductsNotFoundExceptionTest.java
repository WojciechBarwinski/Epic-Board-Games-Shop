package com.wojciechbarwinski.demo.epic_board_games_shop.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductsNotFoundExceptionTest {


    @Test
    void testMessageGenerate(){
        //Given
        List<Long> missingProductsId = List.of(1L, 2L, 3L);
        String expectedMessage = "We can't find products with id = 1, 2, 3";

        //When
        ProductsNotFoundException exception = new ProductsNotFoundException(missingProductsId);

        //Then
        assertEquals(expectedMessage, exception.getMessage());

    }

}