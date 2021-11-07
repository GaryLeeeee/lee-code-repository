package com.garylee.mq.rabbitmq.domain;

import lombok.Data;

/**
 * @author: GaryLeeeee
 * @date: 2021-11-06 13:42
 * @description: rabbitmq测试用实体类
 **/
@Data
public class User {
    private int id;
    private String name;
    private int age;
}
