
package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.AddressDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.*;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.MappingToDTOException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperFacadeTest {

    MapperFacade mapperFacade = new MapperFacade(new OrderMapper());

    @Test
    void shouldMapOrderDTOToOrderEntity(){
        //given
        AddressDTO addressDTO = new AddressDTO("Ulica", "Miasto", "12-345", "123-456-789");
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO("orderer mail",
                addressDTO,
                List.of(new OrderLineDTO(1L, 2)));

        //when
        Order order = mapperFacade.mapOrderRequestDTOToOrderEntity(orderRequestDTO);

        //then
        assertNotNull(order);
        assertNull(order.getId()); //mapper should not map ID because don't have it
        assertNull(order.getOrderLines()); //mapper should not map order lines

        assertEquals(orderRequestDTO.getOrdererMail(), order.getOrdererMail());
        assertEquals(addressDTO.street(), order.getAddress().getStreet());
        assertEquals(addressDTO.city(), order.getAddress().getCity());
        assertEquals(addressDTO.zipCode(), order.getAddress().getZipCode());
        assertEquals(addressDTO.phoneNumber(), order.getAddress().getPhoneNumber());
    }

    @Test
    void shouldMapOrderDTOToOrderEntityWithNullValues(){
        //given
        AddressDTO addressDTO = new AddressDTO(null, null, null, null);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO("orderer mail",
                addressDTO,
                null);

        //when
        Order order = mapperFacade.mapOrderRequestDTOToOrderEntity(orderRequestDTO);

        //then
        assertNotNull(order);
        assertNull(order.getId()); //mapper should not map ID because don't have it
        assertNull(order.getOrderLines()); //mapper should not map order lines
        assertEquals(orderRequestDTO.getOrdererMail(), order.getOrdererMail());
        assertNotNull(order.getAddress());
        assertNull(order.getAddress().getStreet());
        assertNull(order.getAddress().getCity());
        assertNull(order.getAddress().getZipCode());
        assertNull(order.getAddress().getPhoneNumber());
    }

    @Test
    void shouldMapOrderDTOToOrderEntityWithEmptyStrings(){
        //given
        AddressDTO addressDTO = new AddressDTO("", "", "", "");
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO("orderer mail",
                addressDTO,
                List.of(new OrderLineDTO(1L, 2)));

        //when
        Order order = mapperFacade.mapOrderRequestDTOToOrderEntity(orderRequestDTO);

        //then
        assertNotNull(order);
        assertNull(order.getId()); //mapper should not map ID because don't have it
        assertNull(order.getOrderLines()); //mapper should not map order lines

        assertEquals(orderRequestDTO.getOrdererMail(), order.getOrdererMail());
        assertEquals(addressDTO.street(), order.getAddress().getStreet());
        assertEquals(addressDTO.city(), order.getAddress().getCity());
        assertEquals(addressDTO.zipCode(), order.getAddress().getZipCode());
        assertEquals(addressDTO.phoneNumber(), order.getAddress().getPhoneNumber());
    }

    @Test
    void shouldMapOrderToOrderResponseDTO(){
        //given
        Order order = createOrder();

        //when
        OrderResponseDTO orderResponseDTO = mapperFacade.mapOrderToOrderResponseDTO(order);

        //then
        assertNotNull(orderResponseDTO);
        assertEquals(order.getEmployeeId(), orderResponseDTO.getSellerId());
        assertEquals(order.getTotalPrice(), orderResponseDTO.getTotalPrice());
        assertEquals(order.getOrdererMail(), orderResponseDTO.getOrdererMail());
        assertEquals(OrderStatus.PLACED.name(), orderResponseDTO.getStatus());
        assertEquals(order.getAddress().getStreet() , orderResponseDTO.getAddressToSend().street());
        assertEquals(order.getAddress().getCity() , orderResponseDTO.getAddressToSend().city());
        assertEquals(order.getAddress().getZipCode() , orderResponseDTO.getAddressToSend().zipCode());
        assertEquals(order.getAddress().getPhoneNumber() , orderResponseDTO.getAddressToSend().phoneNumber());
        assertEquals(1, order.getOrderLines().size());
        assertEquals(order.getOrderLines().get(0).getQuantity(), orderResponseDTO.getOrderLineDTOs().get(0).quantity());
        assertEquals(order.getOrderLines().get(0).getProduct().getId(), orderResponseDTO.getOrderLineDTOs().get(0).productId());

    }

    @Test
    void shouldThrowExceptionWhenAddressIsNull(){
        Order order = createOrder();
        order.setAddress(null);
        String expectedMessage = "Address to map is null";

        MappingToDTOException exception = assertThrows(MappingToDTOException.class, () -> mapperFacade.mapOrderToOrderResponseDTO(order));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOrderLineIsNull(){
        Order order = createOrder();
        order.setOrderLines(null);
        String expectedMessage = "List of OrderLines are null/empty";

        MappingToDTOException exception = assertThrows(MappingToDTOException.class, () -> mapperFacade.mapOrderToOrderResponseDTO(order));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOrderLineIsEmpty(){
        Order order = createOrder();
        order.setOrderLines(List.of());
        String expectedMessage = "List of OrderLines are null/empty";

        MappingToDTOException exception = assertThrows(MappingToDTOException.class, () -> mapperFacade.mapOrderToOrderResponseDTO(order));

        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Order createOrder() {
        Address address = Address.builder()
                .street("ulica")
                .city("miasto")
                .zipCode("12-345")
                .phoneNumber("123-456-789")
                .build();

        Order order = Order.builder()
                .id(1L)
                .totalPrice(BigDecimal.valueOf(200))
                .ordererMail("orderer mail")
                .employeeId("employee mail")
                .address(address)
                .orderStatus(OrderStatus.PLACED)
                .build();

        Product product = Product.builder()
                .name("product name")
                .id(1L)
                .price(BigDecimal.valueOf(200))
                .build();

        OrderLine orderLine = OrderLine.builder()
                .order(order)
                .product(product)
                .quantity(2)
                .build();

        order.setOrderLines(List.of(orderLine));

        return order;
    }
}
