package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.AddressDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean;
    }


    @Test
    void shouldDetectInvalidCreateOrderRequestDTO() {
        //given
        int expectedNumberOfErrors = 4;
        CreateOrderRequestDTO invalidDTO = createOrderRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(invalidDTO, "createOrderRequestDTO");

        //when
        validator.validate(invalidDTO, bindingResult);

        //then
        assertTrue(bindingResult.hasErrors());
        assertEquals(expectedNumberOfErrors, bindingResult.getFieldErrors().size());

        //TODO if this is necessary, how check all of this errors in correct way?

    }

    CreateOrderRequestDTO createOrderRequest() {

        AddressDTO addressToSend = new AddressDTO("Street", " ", "12345", "79-100-38-36");
        OrderLineDTO orderLine1 = new OrderLineDTO(1L, 1);
        OrderLineDTO orderLine2 = new OrderLineDTO(2L, 1);

        return new CreateOrderRequestDTO("itIsNotAMail", addressToSend, List.of(orderLine1, orderLine2));
    }
}
