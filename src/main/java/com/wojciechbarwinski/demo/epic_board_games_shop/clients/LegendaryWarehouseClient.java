package com.wojciechbarwinski.demo.epic_board_games_shop.clients;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataToWarehouseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "legendaryWarehouseClient", url = "${lw.shipment.url}")
public interface LegendaryWarehouseClient {

    @PostMapping("/order")
    OrderDataFromWarehouseDTO sendOrderToWarehouse(@RequestBody OrderDataToWarehouseDTO orderDataToWarehouseDTO);
}
