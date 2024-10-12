package com.wojciechbarwinski.demo.epic_board_games_shop.payU;


import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.clients.PayUClient;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUAuthException;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUInternalException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
            accessToken = payUClient.getAccessToken(grant_type, client_id, client_secret).getAccess_token();
        } catch (FeignException e) {
            throw new PayUAuthException(e);
        } catch (Exception e) {
            throw new PayUInternalException(e.getMessage());
        }

        PayUOrderRequestDTO payUOrderRequestDTO = payUOrderCreator.createPayUOrderDTOFromOrder(order);

        return payUClient.getPayUOrder(accessToken, payUOrderRequestDTO);
    }
}

