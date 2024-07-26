package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.AddressDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Address;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Product;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceHelperTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceHelper orderHelper;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("username@mail");
    }


    @Test
    void shouldPrepareOrderFromOrderDTOToSave() {
        //given
        OrderRequestDTO orderDTO = createOrderRequestDTO();
        Order order = createOrderInStageAfterMapping();
        String employeeId = "username@mail";
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(175); // 2 x 50 and 3 x 25 look into createProductsListForMock

        when(productRepository.findAllById(Mockito.any())).thenReturn(createProductsListForMock());

        //then
        Order preparedOrder = orderHelper.prepareOrderToSave(orderDTO, order);

        assertEquals(2, preparedOrder.getOrderLines().size());
        assertEquals(expectedTotalPrice, preparedOrder.getTotalPrice());
        assertEquals(OrderStatus.PLACED, preparedOrder.getOrderStatus());
        assertEquals(employeeId, preparedOrder.getEmployeeId());
        assertEquals(2, preparedOrder.getOrderLines().get(0).getQuantity());


    }

    private List<Product> createProductsListForMock() {
        Product product1 = Product.builder()
                .id(1L)
                .name("first product name")
                .price(BigDecimal.valueOf(50))
                .quantity(10)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("second product name")
                .price(BigDecimal.valueOf(25))
                .quantity(10)
                .build();

        return List.of(product1, product2);
    }

    private Order createOrderInStageAfterMapping() {
        Address address = Address.builder()
                .street("Street")
                .city("City")
                .zipCode("ZipCode")
                .phoneNumber("PhoneNumber")
                .build();

        return Order.builder()
                .ordererMail("orderer@example.com")
                .address(address)
                .build();
    }

    private OrderRequestDTO createOrderRequestDTO() {
        AddressDTO addressToSend = new AddressDTO("Street", "City", "ZipCode", "PhoneNumber");
        OrderLineDTO orderLine1 = new OrderLineDTO(1L, 2);
        OrderLineDTO orderLine2 = new OrderLineDTO(2L, 3);

        return new OrderRequestDTO("orderer@example.com", addressToSend, List.of(orderLine1, orderLine2));
    }
}