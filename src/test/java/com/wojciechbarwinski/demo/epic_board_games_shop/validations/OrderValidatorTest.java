package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wojciechbarwinski.demo.epic_board_games_shop.validations.ValidationErrorMatcher.matchesValidationError;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderValidatorTest {

    private final OrderValidator validator = new OrderValidator();


    @Test
    void shouldThrowExceptionWithListOfValidationErrorsWhenProductIdIsIncorrect() {
        //given
        int expectedErrors = 1;
        List<OrderLineDTO> orderLineDTOs = List.of(
                new OrderLineDTO(9L, 3), // wrong product ID
                new OrderLineDTO(3L, 2) // correct OrderLine
        );
        Map<Long, Product> productsFromDB = createMapOfProductForTest();

        //when
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> validator.validateOrderLineDTOS(orderLineDTOs, productsFromDB));

        //then
        assertEquals(expectedErrors, validationException.getErrors().size());
        assertThat(validationException.getErrors().get(0),
                matchesValidationError("orderLineDTO.productId", "There is no product with this id", 9L));

    }

    @Test
    void shouldThrowExceptionWithListOfValidationErrorsWhenProductQuantityIsIncorrect() {
        //given
        int expectedErrors = 1;
        List<OrderLineDTO> orderLineDTOs = List.of(
                new OrderLineDTO(2L, 10), // wrong product quantity
                new OrderLineDTO(3L, 2) // correct OrderLine
        );
        Map<Long, Product> productsFromDB = createMapOfProductForTest();

        //when
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> validator.validateOrderLineDTOS(orderLineDTOs, productsFromDB));

        //then
        assertEquals(expectedErrors, validationException.getErrors().size());
        assertThat(validationException.getErrors().get(0),
                matchesValidationError("orderLineDTO.quantity", "There is insufficient quantity for product with id 2",
                        new OrderLineQuantityValidationRejectedValueDTO(10, 5)));

    }

    private static Map<Long, Product> createMapOfProductForTest() {
        Map<Long, Product> productsFromDB = new HashMap<>();
        productsFromDB.put(1L, new Product(1L, "Name1", BigDecimal.valueOf(10), 2));
        productsFromDB.put(2L, new Product(2L, "Name2", BigDecimal.valueOf(10), 5));
        productsFromDB.put(3L, new Product(3L, "Name2", BigDecimal.valueOf(10), 5));
        return productsFromDB;
    }
}