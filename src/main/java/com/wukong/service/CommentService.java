package com.wukong.service;

import com.wukong.pojo.Comment;

import java.util.List;

/**
 * Created By WuKong on 2022/7/19 15:36
 **/
public interface CommentService {

    //获取评论列表
    List<Comment> listCommentByBlogId(Long id);

    //保存Coment
    int saveComment(Comment comment);
}
