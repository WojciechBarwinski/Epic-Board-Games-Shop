package com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataToWarehouseDTO;

public interface LegendaryWarehousePort {

    OrderDataFromWarehouseDTO sendOrderToWarehouse(OrderDataToWarehouseDTO orderDataToWarehouseDTO);
}
