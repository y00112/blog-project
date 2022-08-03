package com.wukong.service.impl;

import com.wukong.mapper.UserMapper;
import com.wukong.pojo.User;
import com.wukong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created By WuKong on 2022/7/11 14:52
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {

        User user = userMapper.findByUsernameAndPassword(username,password);

        return user;
    }

    @Override
    public User findByUsername(String userName) {
        return userMapper.findByUsername(userName);
    }
}
