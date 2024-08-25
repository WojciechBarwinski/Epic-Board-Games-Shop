package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrderDataToWarehouseDTO {

    private Long id;
    private AddressDTO addressDTO;
    private List<OrderLineDTO> orderLineDTOS;
}
