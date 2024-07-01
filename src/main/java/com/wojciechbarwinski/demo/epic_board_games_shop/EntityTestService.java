package com.wojciechbarwinski.demo.epic_board_games_shop;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityTestService {

    @Autowired
    EntityTestRepository entityTestRepository;

    @PostConstruct
    public void test(){
        EntityTest test = new EntityTest();
        test.setName("test name");
        test.setLastName("test lastName");
        entityTestRepository.save(test);

        for (EntityTest entityTest : entityTestRepository.findAll()) {
            System.out.println(entityTest.getId() + " " +  entityTest.getName());
        }

    }
}
