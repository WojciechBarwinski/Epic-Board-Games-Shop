package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.LoginDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.MissingCredentialsException;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.components.JWTGenerator;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions.InvalidLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) {

        log.debug("Start login username '{}'", loginDto.username());

        checkAndTrowExceptionIfThereIsNoLoginData(loginDto);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.username(),
                            loginDto.password()));

            String token = jwtGenerator.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("User '{}' logged in successfully", loginDto.username());
            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (Exception e) {
            log.warn("Login attempt failed for user '{}': {}", loginDto.username(), e.getMessage());
            throw new InvalidLoginException();
        }
    }

    private void checkAndTrowExceptionIfThereIsNoLoginData(LoginDTO loginDTO) {
        if (loginDTO == null) { //is there any chance that loginDTO will be null?
            log.warn("Login attempt with null LoginDTO");
            throw new MissingCredentialsException();
        }

        validateField(loginDTO.username());
        validateField(loginDTO.password());
    }

    private void validateField(String field) {
        if (field == null || field.trim().isEmpty()) {
            log.warn("Missing or empty field during login attempt");
            throw new MissingCredentialsException();
        }
    }
}
