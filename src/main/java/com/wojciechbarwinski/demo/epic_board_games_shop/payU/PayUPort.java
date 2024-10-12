package com.wojciechbarwinski.demo.epic_board_games_shop.payU;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.payU.dtos.PayUOrderResponseDTO;

public interface PayUPort {

    PayUOrderResponseDTO getPayUOrder(Order order);
}
