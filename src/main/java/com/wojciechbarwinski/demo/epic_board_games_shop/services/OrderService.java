package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.CreateOrderRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.OrderNotFoundException;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MapperFacade mapper;
    private final OrderServiceHelper orderHelper;

    OrderResponseDTO processOrder(CreateOrderRequestDTO createOrderRequestDto) {

        Order order = orderHelper.prepareOrderToSave(createOrderRequestDto,
                mapper.mapCreateOrderRequestDTOToOrderEntity(createOrderRequestDto));

        Order orderAfterSave = orderRepository.save(order);

        return mapper.mapOrderToOrderResponseDTO(orderAfterSave);
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return mapper.mapOrderToOrderResponseDTO(order);

    public List<OrderResponseDTO> getAllOrdersBySearchingData(OrderSearchRequestDTO orderSearchRequestDTO) {

        List<Order> ordersBySearchRequest = orderRepository.findOrdersBySearchRequest(orderSearchRequestDTO);

        return ordersBySearchRequest.stream()
                .map(mapper::mapOrderToOrderResponseDTO)
                .toList();
    }
}
