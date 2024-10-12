package com.wojciechbarwinski.demo.epic_board_games_shop.payU.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUException;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
class PayUOrderClient {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;


    public PayUOrderResponseDTO createOrder(String authorizationToken, PayUOrderRequestDTO orderRequest) {

        String url = "https://secure.snd.payu.com/api/v2_1/orders";
        String jsonRequestBody = null;

        try {
            jsonRequestBody = objectMapper.writeValueAsString(orderRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        RequestBody requestBody = RequestBody.create(jsonRequestBody,
                MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + authorizationToken)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();


        try (Response response = client.newCall(request).execute()) {
            return objectMapper.readValue(response.body().string(), PayUOrderResponseDTO.class);
        } catch (Exception e) {
            throw new PayUException(e.getMessage());
        }

    }
}


