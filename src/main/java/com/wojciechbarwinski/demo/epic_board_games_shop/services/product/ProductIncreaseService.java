package com.wojciechbarwinski.demo.epic_board_games_shop.services.product;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProductIncreaseService {

    private final ProductRepository productRepository;


    @Transactional
    void increaseProductQuantity(List<OrderLine> orderLines) {

        for (OrderLine orderLine : orderLines) {
            productRepository.increaseProductQuantityById(orderLine.getProduct().getId(), orderLine.getQuantity());
        }
    }
}
