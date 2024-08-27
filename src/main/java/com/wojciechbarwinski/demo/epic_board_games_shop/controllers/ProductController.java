package com.wojciechbarwinski.demo.epic_board_games_shop.controllers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.ProductDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.services.ProductServicesFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductServicesFacade productServicesFacade;

    @GetMapping
    List<ProductDTO> getAllProducts() {
        return productServicesFacade.getAllProducts();
    }
}
