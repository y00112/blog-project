package com.wukong.mapper;

import com.wukong.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created By WuKong on 2022/7/11 17:25
 **/
@Mapper
public interface TagMapper {

    int saveTag(Tag tag);

    void deleteTag(Long id);

    int updateTag(Tag tag);

    Tag getTagById(Long id);

    List<Tag> findAll();

    Tag getTagByName(String name);
}
