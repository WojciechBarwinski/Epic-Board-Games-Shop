package com.wojciechbarwinski.demo.epic_board_games_shop.repositories;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
