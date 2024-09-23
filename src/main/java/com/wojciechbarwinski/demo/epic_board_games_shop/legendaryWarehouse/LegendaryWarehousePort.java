package com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;

public interface LegendaryWarehousePort {

    void sendOrderToWarehouse(Order order);
}
