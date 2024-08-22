package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderDataFromWarehouseDTOValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean;
    }

    @Test
    void shouldDetectNoErrorsInOrderDataFromWarehouseDTOV() {
        //given
        OrderDataFromWarehouseDTO correctDTO =
                new OrderDataFromWarehouseDTO(1L, OrderStatus.RECEIVED_BY_WAREHOUSE);
        Errors errors = new BeanPropertyBindingResult(correctDTO, "OrderDataFromWarehouseDTO");

        //when
        validator.validate(correctDTO, errors);

        //then
        assertTrue(errors.getAllErrors().isEmpty());
    }

    @Test
    void shouldDetectErrorsInOrderDataFromWarehouseDTOVWhenIdIsIncorrect() {
        //given
        OrderDataFromWarehouseDTO correctDTO =
                new OrderDataFromWarehouseDTO(-5L, OrderStatus.RECEIVED_BY_WAREHOUSE);
        Errors errors = new BeanPropertyBindingResult(correctDTO, "OrderDataFromWarehouseDTO");

        //when
        validator.validate(correctDTO, errors);

        //then
        assertFalse(errors.getAllErrors().isEmpty());
    }

    @Test
    void shouldDetectErrorsInOrderDataFromWarehouseDTOVWhenStatusIsNull() {
        //given
        OrderDataFromWarehouseDTO correctDTO =
                new OrderDataFromWarehouseDTO(5L, null);
        Errors errors = new BeanPropertyBindingResult(correctDTO, "OrderDataFromWarehouseDTO");

        //when
        validator.validate(correctDTO, errors);

        //then
        assertFalse(errors.getAllErrors().isEmpty());
    }
}