package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateToken(Authentication authentication);
}
