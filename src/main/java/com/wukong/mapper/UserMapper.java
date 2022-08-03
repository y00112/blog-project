package com.wukong.mapper;

import com.wukong.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created By WuKong on 2022/7/11 14:54
 **/
@Mapper
public interface UserMapper {
    User findByUsernameAndPassword(String username,String password);

    User findByUsername(String userName);
}
