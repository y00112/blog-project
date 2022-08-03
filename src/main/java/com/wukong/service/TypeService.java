package com.wukong.service;

import com.wukong.pojo.Type;

import java.util.List;

/**
 * Created By WuKong on 2022/7/11 17:20
 **/
public interface TypeService {

    /**
     * 新增
     * @param type
     * @return
     */
    int saveType(Type type);

    /**
     * 查询
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 分页
     * @return
     */
    List<Type> listType();

    /**
     * 修改
     * @param id
     * @return
     */
    int updateType(Long id,Type type);

    /**
     * 删除
     * @param id
     */
    void deleteType(Long id);

    /**
     * 通过名称查询记录
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    List<Type> findAll();

    List<Type> listTypeOrderByBlogs();

    List<Type> findTypeAndBlogAll();
}
