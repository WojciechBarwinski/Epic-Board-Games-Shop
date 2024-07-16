package com.wojciechbarwinski.demo.epic_board_games_shop.security.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserInMemoryRepositoryImpl implements UserRepository {

    private final List<UserEntity> users;
    private final PasswordEncoder encoder;

    public UserInMemoryRepositoryImpl(List<UserEntity> users,
                                      PasswordEncoder encoder) {
        this.encoder = encoder;
        this.users = createUsers();
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
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
