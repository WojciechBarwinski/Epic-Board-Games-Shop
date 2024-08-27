package com.wojciechbarwinski.demo.epic_board_games_shop.services;

import com.wojciechbarwinski.demo.epic_board_games_shop.EmailSenderService;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderResponseDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.OrderStatus;
import com.wojciechbarwinski.demo.epic_board_games_shop.mappers.MapperFacade;
import com.wojciechbarwinski.demo.epic_board_games_shop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
class OrderProceedAfterConfirmService {

    private final OrderRepository orderRepository;
    private final MapperFacade mapper;
    private final OrderHelper orderHelper;
    private final EmailSenderService emailSenderService;


    OrderResponseDTO proceedOrderAfterConfirm(Long id) {
        Order order = orderHelper.getOrderById(id);

        order.setOrderStatus(OrderStatus.CONFIRMED);
        log.info("Order with id {} was confirm", order.getId());
        emailSenderService.sendSimpleMessage(order.getOrdererMail(),
                "Epic Board Game Shop - Order",
                generateMailText(id));
        orderRepository.save(order);
        return mapper.mapOrderToOrderResponseDTO(order);
    }

    private String generateMailText(Long id) {
        String linkToPay = "http://localhost:8080/order/" + id + "/payment";
        String linkToCancel = "http://localhost:8080/order/" + id + "/cancel";

        StringBuilder mailText = new StringBuilder();

        mailText.append("Thank you for placing your order. Here are some useful links:\n\n");
        mailText.append("1. To pay for your order, click here: ").append(linkToPay).append("\n");
        mailText.append("2. To cancel your order, click here: ").append(linkToCancel).append("\n\n");
        mailText.append("Best regards,\nThe Epic Board Games Shop Team");

        return mailText.toString();
    }
}
