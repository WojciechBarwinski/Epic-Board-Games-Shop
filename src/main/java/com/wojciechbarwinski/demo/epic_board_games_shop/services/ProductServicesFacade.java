package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.ProductDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServicesFacade {

    private final ProductReadService productReadService;
    private final ProductDecreaseService productDecreaseService;
    private final ProductIncreaseService productIncreaseService;

    public List<ProductDTO> getAllProducts(){
        return productReadService.getAllProducts();
    }

    public void decreaseProductQuantity(List<OrderLineDTO> orderLineDTOS){
        productDecreaseService.decreaseProductQuantity(orderLineDTOS);
    }

    public void increaseProductQuantity(List<OrderLine> orderLines){
        productIncreaseService.increaseProductQuantity(orderLines);
    }
}
