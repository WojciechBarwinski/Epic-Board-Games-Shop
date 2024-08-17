package com.wojciechbarwinski.demo.epic_board_games_shop.repositories;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;

import java.util.List;

public interface OrderCriteriaQueryRepository {

    List<Order> findOrdersBySearchRequest(OrderSearchRequestDTO orderSearchRequestDTO);
}
