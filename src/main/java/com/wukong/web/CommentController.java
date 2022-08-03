package com.wukong.web;

import com.wukong.pojo.Comment;
import com.wukong.pojo.User;
import com.wukong.service.BlogService;
import com.wukong.service.CommentService;
import com.wukong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created By WuKong on 2022/7/19 15:28
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Value("${comment.avatar}")
    private String avatar;

    //展示评论
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {

        List<Comment> comments = commentService.listCommentByBlogId(blogId);

        System.out.println("长度"+comments.size());
        System.out.println(comments.isEmpty());
        model.addAttribute("comments", comments);
        model.addAttribute("size", comments.size());

        return "blog :: commentList";
    }


    //评论提交
    @PostMapping("/comments")
    public String post(Comment comment,HttpSession session,Model model){

        Long blogId = comment.getBlogId();

//        comment.setBlog(blogService.getBlog(blogId));
//
//        comment.setAvatar(avatar);

        if (comment.getParentComment() != null){
            if (comment.getParentComment().getId() != null) {
                comment.setParentCommentId(comment.getParentComment().getId());
            }
        }
        User user = (User) session.getAttribute("user");
        //博主
        if (user != null) {
//            User userName = userService.findByUsername(user.getUserName());
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {//非博主
            //设置头像
            comment.setAvatar(avatar);
        }

        if (comment.getParentComment() != null){
            if (comment.getParentComment().getId() != null) {
                comment.setParentCommentId(comment.getParentComment().getId());
            }
        }


        commentService.saveComment(comment);

        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";

//        return "redirect:/comments/" + comment.getBlog().getId();

    }
}
