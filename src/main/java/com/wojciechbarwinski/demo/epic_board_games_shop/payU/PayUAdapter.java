package com.wojciechbarwinski.demo.epic_board_games_shop.payU;


import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.clients.PayUClient;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PayUAdapter implements PayUPort {

    private final PayUClient payUClient;
    private final String currencyCode = "PLN";
    private final String description = "Epic Board Game Shop market";

    @Value("${payU.grant_type}")
    private String grant_type;

    @Value("${payU.client_id}")
    private String client_id;

    @Value("${payU.client_secret}")
    private String client_secret;

    @Value("${payU.merchant_posId}")
    private String merchantPosId;


    @Override
    public PayUOrderResponseDTO getPayUOrder(Order order) {

        String accessToken = payUClient.getAccessToken(grant_type, client_id, client_secret).getAccess_token(); //TODO try-catch
        PayUOrderRequestDTO payUOrderRequestDTO = mapOrderToPayUOrderRequestDTO(order);

        return payUClient.getPayUOrder(accessToken, payUOrderRequestDTO);
    }

    private PayUOrderRequestDTO mapOrderToPayUOrderRequestDTO(Order order) {
        PayUOrderRequestDTO orderRequestDTO = new PayUOrderRequestDTO();
        orderRequestDTO.setCustomerIp("127.0.0.1"); //TODO zautomatyzować pobieranie IP
        orderRequestDTO.setMerchantPosId(merchantPosId);
        orderRequestDTO.setDescription(description);
        orderRequestDTO.setCurrencyCode(currencyCode);
        orderRequestDTO.setTotalAmount(convertToPayUAmount(order.getTotalPrice()));

        List<PayUProductDTO> products = new ArrayList<>();

        for (OrderLine orderLine : order.getOrderLines()) {
            products.add(new PayUProductDTO(
                    orderLine.getProduct().getName(),
                    convertToPayUAmount(orderLine.getProduct().getPrice()),
                    orderLine.getQuantity()));
        }

        orderRequestDTO.setProducts(products);

        return orderRequestDTO;
    }

    private long convertToPayUAmount(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(100)).longValue();
    }
}

