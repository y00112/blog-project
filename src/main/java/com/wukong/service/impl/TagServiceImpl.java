package com.wukong.service.impl;

import com.wukong.exception.MyNotFoundException;
import com.wukong.mapper.BlogMapper;
import com.wukong.mapper.TagMapper;
import com.wukong.pojo.Blog;
import com.wukong.pojo.Tag;
import com.wukong.service.TagService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.swing.BakedArrayList;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created By WuKong on 2022/7/11 17:24
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    BlogMapper blogMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public List<Tag> listTag() {
        List<Tag> tags = tagMapper.findAll();

        //通过tag查询 blog
        for (int i = 0; i < tags.size(); i++) {
            List<Blog> bLog = blogMapper.findBlogByTag(tags.get(i).getId());
            tags.get(i).setBlogs(bLog);
        }

        return tags;
    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag t = tagMapper.getTagById(id);
        if (null == t){
            throw new MyNotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,t);

        return tagMapper.updateTag(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> listTag(String ids) { // ids 1,2,3
        //创建 tags集合
        List<Tag> tags = new ArrayList<>();
        // 通过工具类将ids拆分成集合
        List<Long> longs = convertToList(ids);
        //获取 iterator迭代器
        Iterator<Long> it = longs.iterator();
        //通过it遍历
        while (it.hasNext()){
            Tag tag = tagMapper.getTagById(it.next());
            tags.add(tag);
        }

        return tags;
    }

    //工具类将 ids转成List集合
    private List<Long> convertToList(String ids){
        List<Long> longs = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                longs.add(new Long(idArray[i]));
            }
        }
        return longs;
    }

}
