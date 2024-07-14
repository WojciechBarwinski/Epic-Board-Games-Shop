package com.wojciechbarwinski.demo.epic_board_games_shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/base")
public class BaseRESTController {


    @GetMapping()
    public String getHello(Principal principal){
        return "Hello World to " + principal.getName();
    }
}
