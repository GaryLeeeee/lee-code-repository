package com.garylee.repository.mybatisplus.service;

import com.garylee.repository.LeeDbCodeApplication;
import com.garylee.repository.mybatisplus.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = LeeDbCodeApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void addUser() {
        User user = new User();
        user.setName("first");
        user.setAge(12);
        user.setSex(1);

        System.out.println("result:" + userService.addUser(user));
    }

    @Test
    public void deleteUser() {
        System.out.println("result:" + userService.deleteUser(2));
    }

    @Test
    public void saveOrUpdateUser() {
        User user = new User();
        user.setName("first");
        user.setAge(12);
        user.setSex(3);

        System.out.println("result1:" + userService.saveOrUpdateUser(user));

        //id不存在所以指定无效，只能自增
        user.setId(2);
        System.out.println("result2:" + userService.saveOrUpdateUser(user));

        //id存在执行更新操作
        user.setId(1);
        System.out.println("result3:" + userService.saveOrUpdateUser(user));
    }

    @Test
    public void getUser() {
        User user1 = userService.getUser(1);
        System.out.println("user1:" + user1);
        User user2 = userService.getUser(2);
        System.out.println("user2:" + user2);
    }

    @Test
    public void getUserByParitions() {
        String name = "t";
        int age = 10;
        int sex = 1;
        System.out.println("result:" + userService.getUserByParitions(name, age, sex));
    }

}