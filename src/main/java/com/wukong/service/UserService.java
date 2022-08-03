package com.wukong.service;

import com.wukong.mapper.UserMapper;
import com.wukong.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created By WuKong on 2022/7/11 14:51
 **/
public interface UserService {
    /**
     * 检查用户登录
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username, String password);

    User findByUsername(String userName);
}
