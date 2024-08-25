package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private Long id;

    private String sellerId;

    private BigDecimal totalPrice;

    private String ordererMail;

    private AddressDTO addressToSend;

    private String status;

    private List<OrderLineDTO> orderLineDTOS = new ArrayList<>();
}
