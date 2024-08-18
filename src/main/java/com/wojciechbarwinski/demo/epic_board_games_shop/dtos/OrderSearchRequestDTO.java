package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class OrderSearchRequestDTO {

    private int page;
    private int size;
    private String sortField;
    private SortDirection sortDirection;
    private List<String> ordererMail;
    private List<String> sellerMail;
    private List<String> status;
    private List<String> phone;
    private List<String> city;
    private List<String> street;

}
