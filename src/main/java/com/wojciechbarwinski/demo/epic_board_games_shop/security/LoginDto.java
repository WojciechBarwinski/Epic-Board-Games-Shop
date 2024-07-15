package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
