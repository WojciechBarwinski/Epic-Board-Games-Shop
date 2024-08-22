package com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse;

import com.wojciechbarwinski.demo.epic_board_games_shop.clients.LegendaryWarehouseClient;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataToWarehouseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LegendaryWarehouseAdapter implements LegendaryWarehousePort {

    private final LegendaryWarehouseClient client;

    @Override
    public OrderDataFromWarehouseDTO sendOrderToWarehouse(OrderDataToWarehouseDTO orderDataToWarehouseDTO) {
        return client.sendOrderToWarehouse(orderDataToWarehouseDTO);
    }
}
