package com.wojciechbarwinski.demo.epic_board_games_shop.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserEntity {

    private String username;
    private String password;
    private List<Role> roles;

}
