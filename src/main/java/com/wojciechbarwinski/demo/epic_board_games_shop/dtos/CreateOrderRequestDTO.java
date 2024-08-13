package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDTO {

    @NotBlank(message = "Orderer mail is required")
    @Email(message = "Invalid email format")
    private String ordererMail;

    @Valid
    private AddressDTO addressToSend;

    @Valid
    private List<OrderLineDTO> orderLineDTOs = new ArrayList<>();

}
