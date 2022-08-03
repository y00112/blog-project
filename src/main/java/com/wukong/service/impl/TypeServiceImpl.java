package com.wukong.service.impl;

import com.wukong.exception.MyNotFoundException;
import com.wukong.mapper.TypeMapper;
import com.wukong.pojo.Blog;
import com.wukong.pojo.Type;
import com.wukong.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By WuKong on 2022/7/11 17:24
 **/
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;

    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Override
    public Type getType(Long id) {
        return typeMapper.getTypeById(id);
    }

    @Override
    public List<Type> listType() {
        return typeMapper.findAll();
    }

    @Override
    public int updateType(Long id, Type type) {
        Type t = typeMapper.getTypeById(id);
        if (null == t){
            throw new MyNotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);

        return typeMapper.updateType(t);
    }

    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public List<Type> findAll() {
        return typeMapper.findAll();
    }

    @Override
    public List<Type> listTypeOrderByBlogs() {
        return null;
    }


    @Override
    public List<Type> findTypeAndBlogAll() {
        List<Type> typeAndBlogAll = typeMapper.findTypeAndBlogAll();
        //遍历 type，给每个type 设置 blogs
        for (int i = 0; i < typeAndBlogAll.size(); i++) {
            Long typeId = typeAndBlogAll.get(i).getId();
            List<Blog> blogs =typeMapper.findBlogByTypeId(typeId);
            typeAndBlogAll.get(i).setBlogs(blogs);
        }

        return typeAndBlogAll;
    }

}
