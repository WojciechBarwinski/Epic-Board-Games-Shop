package com.wojciechbarwinski.demo.epic_board_games_shop.security.user;

import com.wojciechbarwinski.demo.epic_board_games_shop.security.exceptions.InvalidLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }


    @Test
    void shouldLoadCorrectUserByUsername() {
        //given
        String username = "owner@mail.com";
        UserEntity user = new UserEntity(username, "password", List.of(Role.OWNER));

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //then
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    void shouldThrowExceptionWhenUsernameWasNotFound() {
        //given
        String username = "owner@mail.com";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        String expectedMessage = "Incorrect login or password";

        //when
        InvalidLoginException exception = assertThrows(InvalidLoginException.class, () -> userDetailsService.loadUserByUsername(username));

        //then
        assertEquals(expectedMessage, exception.getMessage());
    }
}