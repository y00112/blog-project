package com.wukong.service;

import com.wukong.pojo.Tag;

import java.util.List;

/**
 * Created By WuKong on 2022/7/11 17:20
 **/
public interface TagService {

    /**
     * 新增
     * @param tag
     * @return
     */
    int saveTag(Tag tag);

    /**
     * 查询
     * @param id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 分页
     * @return
     */
    List<Tag> listTag();

    /**
     * 修改
     * @param id
     * @return
     */
    int updateTag(Long id,Tag tag);

    /**
     * 删除
     * @param id
     */
    void deleteTag(Long id);

    /**
     * 通过名称查询记录
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    List<Tag> listTag(String ids);

}
