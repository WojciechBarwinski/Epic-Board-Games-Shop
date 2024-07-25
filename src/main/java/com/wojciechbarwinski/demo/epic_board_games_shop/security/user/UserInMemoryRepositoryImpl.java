package com.wojciechbarwinski.demo.epic_board_games_shop.security.user;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserInMemoryRepositoryImpl implements UserRepository {

    private final List<UserEntity> users;
    private final PasswordEncoder encoder;

    @PostConstruct
    private void init() {
        log.trace("initial Users data");
        users.addAll(createUsers());
    }


    @Override
    public Optional<UserEntity> findByUsername(String username) {
        log.trace("find user by username : '{}'", username);
        for (UserEntity user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private List<UserEntity> createUsers() {
        return List.of(
                new UserEntity("owner@mail.com", encoder.encode("owner"), List.of(Role.OWNER)),
                new UserEntity("seller@mail.com", encoder.encode("seller"), List.of(Role.SELLER))
        );
    }
}
