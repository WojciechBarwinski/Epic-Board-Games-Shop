package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.AddressDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Address;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;

import java.util.ArrayList;
import java.util.List;

class OrderMapper {


    Order mapOrderDTOToOrderEntity(OrderRequestDTO orderRequestDTO) {
        return Order.builder()
                .ordererMail(orderRequestDTO.getOrdererMail())
                .address(mapAddressDTOToAddress(orderRequestDTO.getAddressToSend()))
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
                .sellerId(order.getEmployeeId())
                .totalPrice(order.getTotalPrice())
                .ordererMail(order.getOrdererMail())
                .addressToSend(mapAddressToAddressDTO(order.getAddress()))
                .orderLineDTOList(mapOrderLineToOrderLineDTO(order.getOrderLines()))
                .build();
    }

    private AddressDTO mapAddressToAddressDTO(Address address) {
        return new AddressDTO(
                address.getStreet(),
                address.getCity(),
                address.getZipCode(),
                address.getPhoneNumber()
        );
    }

    private List<OrderLineDTO> mapOrderLineToOrderLineDTO(List<OrderLine> orderLines) {
        List<OrderLineDTO> orderLinesDTO = new ArrayList<>();

        for (OrderLine orderLine : orderLines) {
            orderLinesDTO.add(new OrderLineDTO(
                    orderLine.getProduct().getId(), orderLine.getQuantity()));
        }
        return orderLinesDTO;
    }
}
