package com.wukong.mapper;

import com.wukong.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created By WuKong on 2022/7/19 15:39
 **/
@Mapper
public interface CommentMapper {

    List<Comment> listCommentByBlogId(Long id);

    int saveComment(Comment comment);

    Comment getCommentById(Long parentCommentId);

    //查询顶级评论
    List<Comment> listParentComment(Long blogId, Long parentId);

    //查询一级评论
    List<Comment> listByBlogIdAndParentId(Long blogId, Long parentId);
}
