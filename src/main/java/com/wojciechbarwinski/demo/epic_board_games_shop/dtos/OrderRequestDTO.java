package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private String ordererMail;

    private AddressDTO addressToSend;

    private List<OrderLineDTO> orderLineDTOs;

}
