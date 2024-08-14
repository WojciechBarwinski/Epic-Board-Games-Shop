package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class OrderSearchRequest {

    private String fieldToFilter;
    private String fieldFilterValue;
    private int page = 0;
    private int size = 10;
    private List<SortValue> sortValues;
}
