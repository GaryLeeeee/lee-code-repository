package com.garylee.mq.rabbitmq.controller;

import com.garylee.mq.rabbitmq.domain.User;
import com.garylee.mq.rabbitmq.service.RabbitMqProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GaryLeeeee
 * @date: 2021-11-06 15:31
 * @description: 测试
 **/
@RestController
public class RabbitMqTestController {
    @Autowired
    private RabbitMqProvider rabbitMqProvider;

    @RequestMapping("/sendRabbitMq")
    public String sendRabbitMq(User user) {
        rabbitMqProvider.send(user);

        return "success";
    }
}
