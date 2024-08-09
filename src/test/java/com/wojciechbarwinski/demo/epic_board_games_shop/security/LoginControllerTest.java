package com.wojciechbarwinski.demo.epic_board_games_shop.security;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.LoginDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.exceptions.MissingCredentialsException;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.components.JWTGenerator;
import com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions.InvalidLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private JWTGenerator jwtGenerator;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);

        loginController = new LoginController(jwtGenerator, authenticationManager);
    }

    @Test
    void shouldLoginSuccessfully() {
        //given
        LoginDTO loginDto = new LoginDTO("username", "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtGenerator.generateToken(authentication))
                .thenReturn("mockToken");

        //when
        ResponseEntity<String> response = loginController.login(loginDto);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockToken", response.getBody());
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoLoginInformation() {
        //given
        LoginDTO loginDto = new LoginDTO("", "");
        String expectedMessage = "Login data are null/blank";

        //when
        MissingCredentialsException exception = assertThrows(MissingCredentialsException.class, () -> loginController.login(loginDto));

        //then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenThereIsAnyLoginInvalidData() {
        //given
        String expectedMessage = "Incorrect login or password";
        LoginDTO loginDto = new LoginDTO("wrongUsername", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException(""));

        //when
        InvalidLoginException exception = assertThrows(InvalidLoginException.class, () -> loginController.login(loginDto));

        //then
        assertEquals(expectedMessage, exception.getMessage());
    }
}