package com.wojciechbarwinski.demo.epic_board_games_shop.legendaryWarehouse;

import com.wojciechbarwinski.demo.epic_board_games_shop.clients.LegendaryWarehouseClient;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataFromWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderDataToWarehouseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LegendaryWarehouseAdapter implements LegendaryWarehousePort {

    private final LegendaryWarehouseClient client;
    private final MapperFacade mapper;
    private final OrderRepository orderRepository;


    @Override
    public void sendOrderToWarehouse(Order order) {
        OrderDataToWarehouseDTO orderDataToWarehouseDTO = mapper.mapOrderToOrderDataToWarehouse(order);
        log.info("Order with id {} is being sent to the warehouse...", order.getId());

        try {
            OrderDataFromWarehouseDTO orderDataFromWarehouseDTO = client.sendOrderToWarehouse(orderDataToWarehouseDTO);
            log.info("Order with id {} was send to the warehouse successfully", order.getId());
            order.setOrderStatus(orderDataFromWarehouseDTO.status());
        } catch (Exception e) {
            log.warn("Order with id {} was NOT sent to the warehouse successfully", order.getId(), e);
        }

        orderRepository.save(order);
    }
}
