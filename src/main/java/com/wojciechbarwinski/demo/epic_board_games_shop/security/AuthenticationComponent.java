package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions.InvalidSellerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationComponent {

    public String getSellerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }

        log.error("Someone try proceed order without being login!");
        throw new InvalidSellerException();
    }
}
