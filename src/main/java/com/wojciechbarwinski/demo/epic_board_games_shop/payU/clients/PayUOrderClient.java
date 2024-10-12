package com.wojciechbarwinski.demo.epic_board_games_shop.payU.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUInternalException;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUNetworkException;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.exceptions.PayUOrderExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
class PayUOrderClient {

    private static final String CONTENT_TYPE = "application/json";
    private static final String ACCEPT_TYPE = "application/json";
    private static final int payUCreateOrderCodeResponse = 302;

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    @Value("${payU.order_url}")
    private String url;


    public PayUOrderResponseDTO createOrder(String authorizationToken, PayUOrderRequestDTO orderRequest) {

        String jsonRequestBody = serializeOrderRequestToJSON(orderRequest);
        Request request = buildHttpRequest(authorizationToken, jsonRequestBody);

        return executeRequest(request);
    }

    private String serializeOrderRequestToJSON(PayUOrderRequestDTO orderRequest) {
        try {
            return objectMapper.writeValueAsString(orderRequest);
        } catch (JsonProcessingException e) {
            log.warn("Error serializing order request: {}", e.getMessage());
            throw new RuntimeException("Error serializing order request: " + e.getMessage(), e);
        }
    }

    private Request buildHttpRequest(String authorizationToken, String jsonRequestBody) {

        RequestBody requestBody = RequestBody.create(jsonRequestBody,
                MediaType.parse("application/json"));

        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + authorizationToken)
                .addHeader("Content-Type", CONTENT_TYPE)
                .addHeader("Accept", ACCEPT_TYPE)
                .build();
    }

    private PayUOrderResponseDTO executeRequest(Request request) {

        log.info("Executing HTTP request with order to PayU.");
        try (Response response = client.newCall(request).execute()) {
            if (response.code() != payUCreateOrderCodeResponse) {
                log.warn("Unsuccessful response from PayU. HTTP status: {}", response.code());
                throw new PayUOrderExecutionException(response.code());
            }
            log.info("Successfully received PayU order response.");
            return objectMapper.readValue(response.body().string(), PayUOrderResponseDTO.class);
        } catch (IOException e) {
            log.warn("Network error during request execution: {}", e.getMessage());
            throw new PayUNetworkException(e.getMessage());
        } catch (Exception e) {
            log.warn("Unexpected error during request execution: {}", e.getMessage());
            throw new PayUInternalException(e.getMessage());
        }
    }
}


