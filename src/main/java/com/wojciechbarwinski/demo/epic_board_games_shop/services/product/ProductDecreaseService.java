package com.wojciechbarwinski.demo.epic_board_games_shop.services.product;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.ProductStockCouldNotBeDecreasedException;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProductDecreaseService {

    private final ProductRepository productRepository;

    @Transactional
    void decreaseProductQuantity(List<OrderLineDTO> orderLineDTOS) {

        for (OrderLineDTO orderLine : orderLineDTOS) {
            int updatedRecordsCount = productRepository.decreaseProductQuantityById(orderLine.productId(), orderLine.quantity());

            if (updatedRecordsCount == 0) {
                throw new ProductStockCouldNotBeDecreasedException(orderLine.productId(), orderLine.quantity());
            }
        }
    }
}
