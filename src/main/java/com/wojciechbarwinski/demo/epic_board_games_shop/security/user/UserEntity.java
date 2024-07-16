package com.wojciechbarwinski.demo.epic_board_games_shop.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserEntity {

    private String username;
    private String password;
    private List<Role> roles;

}
