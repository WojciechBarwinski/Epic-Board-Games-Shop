package com.wojciechbarwinski.demo.epic_board_games_shop.payU;


import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.clients.PayUClient;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUAuthException;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUInternalException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayUAdapter implements PayUPort {

    private final PayUClient payUClient;
    private final PayUOrderCreator payUOrderCreator;

    @Value("${payU.grant_type}")
    private String grant_type;

    @Value("${payU.client_id}")
    private String client_id;

    @Value("${payU.client_secret}")
    private String client_secret;


    @Override
    public PayUOrderResponseDTO getPayUOrder(Order order) {
        String accessToken;

        try {
            log.info("Attempting to retrieve access token from PayU...");
            accessToken = payUClient.getAccessToken(grant_type, client_id, client_secret).getAccess_token();
            log.info("Access token retrieved successfully.");
        } catch (FeignException e) {
            log.warn("Failed to retrieve access token: {}", e.getMessage());
            throw new PayUAuthException(e);
        } catch (Exception e) {
            log.warn("Unexpected error occurred during access token retrieval: {}", e.getMessage());
            throw new PayUInternalException(e.getMessage());
        }

        PayUOrderRequestDTO payUOrderRequestDTO = payUOrderCreator.createPayUOrderDTOFromOrder(order);

        return payUClient.getPayUOrder(accessToken, payUOrderRequestDTO);
    }
}

