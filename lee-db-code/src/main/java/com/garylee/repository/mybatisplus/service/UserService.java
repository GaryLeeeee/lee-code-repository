package com.garylee.repository.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.garylee.repository.mybatisplus.domain.User;
import com.garylee.repository.mybatisplus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @desc
 * @author GaryLeeeee
 * @date 2021/07/20 21:12
 *
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加
     * @param user
     * @return
     */
    public int addUser(User user){
        return userMapper.insert(user);
    }

    /**
     * 根据id删除
     * @param id
     * @return 删除成功返回1，失败返回0
     */
    public int deleteUser(int id){
        return userMapper.deleteById(id);
    }

    /**
     * 新增/删除(id已存在则更新，不存在则新增)
     * @param user
     * @return
     */
    public boolean saveOrUpdateUser(User user){
        return this.saveOrUpdate(user);
    }

    /**
     * 根据主键查询
     * @param id
     * @return 存在则为User对象，不存在则为null
     */
    public User getUser(int id){
        return userMapper.selectOne(
                new QueryWrapper<User>()
                .lambda()
                .eq(User::getId, id)
        );
    }

    /**
     * 多条件查询(列表)
     */
    public List<User> getUserByParitions(String name, int age, int sex){
        return userMapper.selectList(
                new QueryWrapper<User>()
                .lambda()
                .like(User::getName, name)
                .gt(User::getAge, age)
                .eq(User::getSex, sex)
        );
    }

}
