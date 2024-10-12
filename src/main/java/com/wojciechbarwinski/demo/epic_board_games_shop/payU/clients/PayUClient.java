package com.wojciechbarwinski.demo.epic_board_games_shop.payU.clients;

import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUTokenResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;

public interface PayUClient {

    PayUTokenResponseDTO getAccessToken(String grantType,
                                        String clientId,
                                        String clientSecret);

    PayUOrderResponseDTO getPayUOrder(String token,
                                      PayUOrderRequestDTO payUOrderRequestDTO);
}
