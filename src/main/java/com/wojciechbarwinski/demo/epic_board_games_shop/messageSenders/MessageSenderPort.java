package com.wojciechbarwinski.demo.epic_board_games_shop.messageSenders;

import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;

public interface MessageSenderPort {

    void sendSimpleMessageAfterOrderWasConfirmed(Order order);
}
