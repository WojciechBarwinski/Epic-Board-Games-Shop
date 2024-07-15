package com.wojciechbarwinski.demo.epic_board_games_shop;

import com.wojciechbarwinski.demo.epic_board_games_shop.security.keys.KeyPairGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private KeyPairGeneratorService keyPairGeneratorService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            keyPairGeneratorService.generateAndSaveKeys();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Keys generated and saved successfully.");

    }
}
