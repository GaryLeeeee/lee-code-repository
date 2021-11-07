package com.garylee.mq.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: GaryLeeeee
 * @date: 2021-11-04 00:05
 * @description: rabbitmq配置类
 **/
@Configuration
public class RabbitMqConfiguration {

    @Bean(name = "leeConnectionFactory")
    public ConnectionFactory leeConnectionFactory(
            @Value("${spring.lee-rabbitmq.addresses}") String addresses,
            @Value("${spring.lee-rabbitmq.username}") String username,
            @Value("${spring.lee-rabbitmq.password}") String password,
            @Value("${spring.lee-rabbitmq.virtual-host}") String virtualHost
    ) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    /**
     * 生产者配置
     *
     * @param connectionFactory
     * @return
     */
    @Bean(name = "leeRabbitTemplate")
    public RabbitTemplate leeRabbitTemplate(
            @Qualifier("leeConnectionFactory") ConnectionFactory connectionFactory
    ) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    @Bean(name = "leeQueue")
    public Queue leeQueue() {
        return new Queue(RabbitMqConstant.QUEUE);
    }

    @Bean(name = "leeExchange")
    public TopicExchange leeExchange() {
        return new TopicExchange(RabbitMqConstant.EXCHANGE);
    }

    @Bean
    Binding binding(@Qualifier("leeQueue") Queue leeQueue, @Qualifier("leeExchange") TopicExchange leeExchange) {
        return BindingBuilder.bind(leeQueue).to(leeExchange).with(RabbitMqConstant.ROUTING_KEY);
    }

    /**
     * 消费者配置
     */
    @Bean(name = "leeFactory")
    public SimpleRabbitListenerContainerFactory leeFactory(
            @Qualifier("leeConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}
