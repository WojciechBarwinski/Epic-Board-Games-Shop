package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.LoginDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.InvalidLoginException;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.components.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.username(),
                            loginDto.password()));

            String token = jwtGenerator.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (Exception e) {
            throw new InvalidLoginException();
        }
    }
}
