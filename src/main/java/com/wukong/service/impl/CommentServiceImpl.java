package com.wukong.service.impl;

import com.wukong.mapper.CommentMapper;
import com.wukong.pojo.Comment;
import com.wukong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created By WuKong on 2022/7/19 15:37
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //拿到所有的评论数据
//        List<Comment> comments = commentMapper.listCommentByBlogId(blogId);

        //查询顶级评论
        List<Comment> parentComments = commentMapper.listParentComment(blogId,Long.parseLong("-1"));
        for (Comment comment:parentComments) {
            Long parentId = comment.getId();
            String parentNickName1 = comment.getNickName();
            //通过bolgId 和 parentId查询顶级评论的子评论
            List<Comment> childComments = commentMapper.listByBlogIdAndParentId(blogId,parentId);
            combineChildren(blogId, childComments, parentNickName1);
            comment.setReplyComment(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return parentComments;
    }

    @Override
    public int saveComment(Comment comment) {
        Long parentCommentId = comment.getParentCommentId();
        if (parentCommentId != -1){
            //查询回复的父对象
            comment.setParentComment(commentMapper.getCommentById(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }



    private void combineChildren(Long blogId, List<Comment> childComments, String parentNickname1) {
//        判断是否有子评论
        if(childComments.size() > 0){
//                循环找出子评论的id
            for(Comment childComment : childComments){
                String parentNickname = childComment.getNickName();
                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);
                Long childId = childComment.getId();
//               回调子自别评论的子评论
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    private void recursively(Long blogId, Long childId, String parentNickname1) {
//      回调子自别评论的子评论
        List<Comment> replayComments = commentMapper.listByBlogIdAndParentId(blogId,childId);
//      判断是否有子评论
        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickName();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }



}
