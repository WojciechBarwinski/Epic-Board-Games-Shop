package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
class OrderReadService {

    private final OrderRepository orderRepository;
    private final MapperFacade mapper;
    private final OrderHelper orderHelper;

    OrderResponseDTO getOrderById(Long id) {
        Order order = orderHelper.getOrderById(id);

        return mapper.mapOrderToOrderResponseDTO(order);
    }

    List<OrderResponseDTO> getAllOrdersBySearchingData(OrderSearchRequestDTO orderSearchRequestDTO) {

        List<Order> ordersBySearchRequest = orderRepository.findOrdersBySearchRequest(orderSearchRequestDTO);

        return ordersBySearchRequest.stream()
                .map(mapper::mapOrderToOrderResponseDTO)
                .toList();
    }

}
