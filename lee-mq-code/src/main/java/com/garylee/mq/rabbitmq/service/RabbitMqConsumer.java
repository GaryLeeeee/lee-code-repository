package com.garylee.mq.rabbitmq.service;

import com.alibaba.fastjson.JSONObject;
import com.garylee.mq.rabbitmq.config.RabbitMqConstant;
import com.garylee.mq.rabbitmq.domain.User;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author: GaryLeeeee
 * @date: 2021-11-06 19:13
 * @description: rabbitmq消费者
 **/
@Slf4j
@Component
public class RabbitMqConsumer {

    @RabbitListener(queues = RabbitMqConstant.QUEUE, containerFactory = "leeFactory")
    public void process(Channel channel, Message message) {
        log.info("[消费MQ] message:{}", message);

        try {

            String messageVal = new String(message.getBody(), Charset.defaultCharset().name());
            User user = JSONObject.parseObject(messageVal, User.class);
            log.info("[消费MQ] user:{}", user);
        } catch (Exception e) {
            log.error("[消费MQ]消费异常", e);
        }

        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("[消费MQ]ACK异常", e);
        }
    }
}
