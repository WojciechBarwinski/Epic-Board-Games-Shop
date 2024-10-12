package com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos;


import lombok.Data;

@Data
public class PayUOrderResponseDTO {
    private String orderId;
    private String redirectUri;
    private Status status;

    @Data
    public static class Status {
        private String statusCode;
    }
}