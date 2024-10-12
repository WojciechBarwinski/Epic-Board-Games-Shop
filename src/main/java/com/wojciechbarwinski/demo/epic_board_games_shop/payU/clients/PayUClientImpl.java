package com.wojciechbarwinski.demo.epic_board_games_shop.payU.clients;

import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUTokenResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayUClientImpl implements PayUClient{

    private final PayUAuthClient payUAuthClient;
    private final PayUOrderClient payUOrderClient;

    @Override
    public PayUTokenResponseDTO getAccessToken(String grantType, String clientId, String clientSecret) {
        return payUAuthClient.getAccessToken(grantType, clientId, clientSecret);
    }

    @Override
    public PayUOrderResponseDTO getPayUOrder(String token, PayUOrderRequestDTO payUOrderRequestDTO) {
        return payUOrderClient.createOrder(token, payUOrderRequestDTO);
    }
}
