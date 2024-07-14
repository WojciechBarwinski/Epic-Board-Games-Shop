package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import java.util.Optional;


public interface UserRepository {

    Optional<User> findByUsername(String username);
}
