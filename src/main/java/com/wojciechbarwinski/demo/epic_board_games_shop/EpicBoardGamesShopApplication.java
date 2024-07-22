package com.wojciechbarwinski.demo.epic_board_games_shop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EpicBoardGamesShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpicBoardGamesShopApplication.class, args);
		log.info("App starts");
	}
}
