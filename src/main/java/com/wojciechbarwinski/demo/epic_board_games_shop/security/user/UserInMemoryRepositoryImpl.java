package com.wojciechbarwinski.demo.epic_board_games_shop.security.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserInMemoryRepositoryImpl implements UserRepository{

    private final List<User> users;
    private final BCryptPasswordEncoder encoder;

    public UserInMemoryRepositoryImpl(List<User> users, BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
        this.users = createUsers();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private List<User> createUsers(){
        return List.of(
                new User("owner@mail.com", encoder.encode("owner"), Role.OWNER),
                new User("seller@mail.com", encoder.encode("seller"), Role.SELLER)
        );
    }
}
