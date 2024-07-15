package com.wojciechbarwinski.demo.epic_board_games_shop;

import com.wojciechbarwinski.demo.epic_board_games_shop.security.keys.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class EpicBoardGamesShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpicBoardGamesShopApplication.class, args);
	}

}
