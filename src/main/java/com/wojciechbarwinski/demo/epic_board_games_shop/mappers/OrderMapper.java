package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.AddressDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Address;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.MappingToDTOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
class OrderMapper {


    Order mapOrderRequestDTOToOrderEntity(CreateOrderRequestDTO createOrderRequestDto) {
        return Order.builder()
                .ordererMail(createOrderRequestDto.getOrdererMail())
                .address(mapAddressDTOToAddress(createOrderRequestDto.getAddressToSend()))
                .build();
    }

    private Address mapAddressDTOToAddress(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.street())
                .city(addressDTO.city())
                .zipCode(addressDTO.zipCode())
                .phoneNumber(addressDTO.phoneNumber())
                .build();
    }

    OrderResponseDTO mapOrderToOrderResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .sellerId(order.getEmployeeId())
                .totalPrice(order.getTotalPrice())
                .ordererMail(order.getOrdererMail())
                .addressToSend(mapAddressToAddressDTO(order.getAddress()))
                .status(order.getOrderStatus().name())
                .orderLineDTOs(mapOrderLineToOrderLineDTO(order.getOrderLines()))
                .build();
    }

    private AddressDTO mapAddressToAddressDTO(Address address) {
        if (address == null){
            log.warn("Address is null");
            throw new MappingToDTOException("Address to map is null");
        }
        return new AddressDTO(
                address.getStreet(),
                address.getCity(),
                address.getZipCode(),
                address.getPhoneNumber()
        );
    }

    private List<OrderLineDTO> mapOrderLineToOrderLineDTO(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()){
            log.warn("Order has no Order line");
            throw new MappingToDTOException("List of OrderLines are null/empty");
        }
        List<OrderLineDTO> orderLinesDTO = new ArrayList<>();

        for (OrderLine orderLine : orderLines) {
            orderLinesDTO.add(new OrderLineDTO(
                    orderLine.getProduct().getId(), orderLine.getQuantity()));
        }
        return orderLinesDTO;
    }
}
