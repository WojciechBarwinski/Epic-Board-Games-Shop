package com.wojciechbarwinski.demo.epic_board_games_shop.payments;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentPort paymentPort;

        public PaymentDataDTO createPaymentData(Order order){

            return paymentPort.createPaymentData(order);
        }

}
