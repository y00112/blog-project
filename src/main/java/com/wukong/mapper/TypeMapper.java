package com.wukong.mapper;

import com.wukong.pojo.Blog;
import com.wukong.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created By WuKong on 2022/7/11 17:25
 **/
@Mapper
public interface TypeMapper {

    int saveType(Type type);

    void deleteType(Long id);

    int updateType(Type type);

    Type getTypeById(Long id);

    List<Type> findAll();

    Type getTypeByName(String name);

    List<Type> findTypeAndBlogAll();

    List<Blog> findBlogByTypeId(Long typeId);
}
