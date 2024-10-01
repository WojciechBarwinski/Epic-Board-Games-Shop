package com.wojciechbarwinski.demo.epic_board_games_shop.repositories;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCriteriaQueryRepository {


    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderLines WHERE o.orderStatus = :status AND o.statusUpdatedAt < :thresholdTime")
    List<Order> findByOrderStatusAndStatusUpdatedAtBefore(@Param("status") OrderStatus status,
                                                          @Param("thresholdTime") LocalDateTime thresholdTime);


    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
