package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.AddressDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("integration")
class OrderCreateServiceIntegrationTest {

    @Autowired
    private OrderCreateService orderCreateService;

    @Test
    @WithMockUser(username = "owner@mail.com")
    void integrationCreateOrder() {
        //given
        CreateOrderRequestDTO orderRequest = createOrderRequest();
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(748.49); // product id1 = 525.50 x1 quantity, id2 = 222.99x1 quantity  SUM 748.49
        String expectedStatus = OrderStatus.PLACED.name();
        int expectedListDTOSize = 2;
        String expectedOrdererMail = "orderer@example.com";
        String expectedAddressPhoneNumber = "PhoneNumber";

        //when
        OrderResponseDTO orderResponse = orderCreateService.createOrder(orderRequest);

        //then
        assertNotNull(orderResponse.getId());
        assertEquals("owner@mail.com", orderResponse.getSellerId());
        assertEquals(expectedTotalPrice, orderResponse.getTotalPrice());
        assertEquals(expectedStatus, orderResponse.getStatus());
        assertEquals(expectedListDTOSize, orderResponse.getOrderLineDTOS().size());
        assertEquals(expectedOrdererMail, orderResponse.getOrdererMail());
        assertEquals(expectedOrdererMail, orderResponse.getOrdererMail());
        assertEquals(expectedAddressPhoneNumber, orderResponse.getAddressToSend().phoneNumber());
    }


    CreateOrderRequestDTO createOrderRequest() {

        AddressDTO addressToSend = new AddressDTO("Street", "City", "ZipCode", "PhoneNumber");
        OrderLineDTO orderLine1 = new OrderLineDTO(1L, 1);
        OrderLineDTO orderLine2 = new OrderLineDTO(2L, 1);

        return new CreateOrderRequestDTO("orderer@example.com", addressToSend, List.of(orderLine1, orderLine2));
    }
}