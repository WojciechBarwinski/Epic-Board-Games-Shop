package com.wojciechbarwinski.demo.epic_board_games_shop.repositories;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCriteriaQueryRepository {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findByOrderStatusAndStatusUpdatedAtBefore(OrderStatus status, LocalDateTime dateTime);

}
