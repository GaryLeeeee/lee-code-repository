package com.garylee.mq.rabbitmq.service;

import com.garylee.mq.rabbitmq.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class RabbitMqProviderTest {
    @Autowired
    private RabbitMqProvider rabbitMqProvider;

    @Test
    void send() {
        User user = new User();
        user.setId(123);
        user.setName("rabbitmqxxx");
        user.setAge(18);
        rabbitMqProvider.send(user);
    }
}