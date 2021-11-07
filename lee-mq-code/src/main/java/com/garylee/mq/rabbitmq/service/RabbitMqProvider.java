package com.garylee.mq.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.garylee.mq.rabbitmq.config.RabbitMqConstant;
import com.garylee.mq.rabbitmq.domain.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: GaryLeeeee
 * @date: 2021-11-06 13:40
 * @description: rabbitmq生产者
 **/
@Service
public class RabbitMqProvider {

    @Resource(name = "leeRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public void send(User user) {
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE, RabbitMqConstant.ROUTING_KEY, JSON.toJSONString(user));
    }
}
