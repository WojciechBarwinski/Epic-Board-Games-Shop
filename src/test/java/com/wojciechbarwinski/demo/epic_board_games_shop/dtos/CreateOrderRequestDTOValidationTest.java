package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CreateOrderRequestDTOValidationTest {


    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean;
    }


    @Test
    void shouldDetectValidationErrorsInCreateOrderRequestDTO() {
        CreateOrderRequestDTO orderRequestWithIncorrectMail = provideOrderRequestDTOWithIncorrectMail();
        Errors errors = new BeanPropertyBindingResult(orderRequestWithIncorrectMail, "createOrderRequestDTO");

        //when
        validator.validate(orderRequestWithIncorrectMail, errors);

        // then
        assertFalse(errors.getAllErrors().isEmpty(), "Validation should detect errors for the provided createOrderRequestDTO");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidAddressDTOS")
    void shouldDetectValidationErrorsInAddressDTO(AddressDTO address) {
        //given
        Errors errors = new BeanPropertyBindingResult(address, "addressDTO");

        //when
        validator.validate(address, errors);

        // then
        assertFalse(errors.getAllErrors().isEmpty(), "Validation should detect errors for the provided addressDTO");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidOrderLineDTOS")
    void shouldDetectValidationErrorsInOrderLineDTO(OrderLineDTO orderLineDTO) {
        //given
        Errors errors = new BeanPropertyBindingResult(orderLineDTO, "orderLineDTO");

        //when
        validator.validate(orderLineDTO, errors);

        // then
        assertFalse(errors.getAllErrors().isEmpty(), "Validation should detect errors for the provided orderLineDTO");
    }

    private CreateOrderRequestDTO provideOrderRequestDTOWithIncorrectMail() {

        AddressDTO addressToSend = new AddressDTO("Main St", "New York", "12-345", "123456789");
        OrderLineDTO orderLine1 = new OrderLineDTO(1L, 1);
        OrderLineDTO orderLine2 = new OrderLineDTO(2L, 1);

        return new CreateOrderRequestDTO("test&mail.com", addressToSend, List.of(orderLine1, orderLine2));
    }

    private static Stream<AddressDTO> provideInvalidAddressDTOS() {
        return Stream.of(
                new AddressDTO("", "New York", "12-345", "123456789"), // Invalid street
                new AddressDTO("Main St", "", "12-345", "123456789"), // Invalid city
                new AddressDTO("Main St", "New York", "12345", "123456789"), // Invalid zip code
                new AddressDTO("Main St", "New York", "12-345", ""), // Invalid phone number
                new AddressDTO("Main St", "New York", "12-345", "12345678") // Invalid phone number format
        );
    }

    private static Stream<OrderLineDTO> provideInvalidOrderLineDTOS() {
        return Stream.of(
                new OrderLineDTO(-1L, 5),
                new OrderLineDTO(3L, 0),
                new OrderLineDTO(3L, -3),
                new OrderLineDTO(null, -3)
        );
    }
}
