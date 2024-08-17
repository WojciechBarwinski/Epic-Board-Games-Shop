package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class OrderSearchRequestDTO {

    private int page = 0;
    private int size = 10;
    private Map<String, String> filters;
    private Map<String, SortDirection> sorts;
}
