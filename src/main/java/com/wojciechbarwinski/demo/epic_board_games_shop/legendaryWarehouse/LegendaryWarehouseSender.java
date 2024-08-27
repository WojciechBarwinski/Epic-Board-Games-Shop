package com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataToWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegendaryWarehouseSender {

    private final LegendaryWarehousePort port;
    private final MapperFacade mapper;
    private final OrderRepository orderRepository;

    public void sendOrderToLegendaryWarehouse(Order order){

        OrderDataToWarehouseDTO orderDataToWarehouseDTO = mapper.mapOrderToOrderDataToWarehouse(order);
        log.info("Order with id {} is sending to warehouse...", order.getId());

        try {
            OrderDataFromWarehouseDTO orderDataFromWarehouseDTO = port.sendOrderToWarehouse(orderDataToWarehouseDTO);
            order.setOrderStatus(orderDataFromWarehouseDTO.status());
        } catch (Exception e) {
            log.warn("Order with id {} was NOT sending to warehouse successfully", order.getId(), e);
        }

        log.info("Order with id {} was sending to warehouse successfully", order.getId());

        orderRepository.save(order);
    }
}
