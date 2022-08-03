package com.wukong.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.dto.BlogQuery;
import com.wukong.dto.SearchBlog;
import com.wukong.pojo.Blog;
import com.wukong.pojo.Type;
import com.wukong.pojo.User;
import com.wukong.service.BlogService;
import com.wukong.service.TagService;
import com.wukong.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Types;
import java.util.*;

/**
 * Created By WuKong on 2022/7/11 16:35
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /*  分页查找blog列表 */
    @GetMapping("/blogs")
    public String list(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,BlogQuery blogQuery,Model model){
        PageHelper.startPage(pageNum,5);
        List<BlogQuery> blogs = blogService.findBlogQuery();
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("types",typeService.findAll());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs";
    }


    /*  根据条件查询博客内容 */
    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum, SearchBlog searchBlog,Model model){
        PageHelper.startPage(pageNum,5);
        List<BlogQuery> blogs = blogService.getBlogBySearch(searchBlog);

        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs :: blogList";
    }


    /* 跳转到新增页面 */
    @GetMapping("/blogs/input")
    public String input(Model model){
        //初始化
        initTypeAndTag(model);

        model.addAttribute("blog",new Blog());

        return "admin/blogs-input";
    }

    /* 跳转到修改页面 */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        //初始化
        initTypeAndTag(model);

        Blog blog = blogService.getBlog(id);


        model.addAttribute("blog",blog);

        return "admin/blogs-input";
    }

    public void initTypeAndTag(Model model){
        //初始化分类
        model.addAttribute("types",typeService.findAll());
        //初始化标签
        model.addAttribute("tags",tagService.listTag());

    }

    /* 新增 + 更新*/
    @PostMapping ("/blogs")
    public String save(Blog blog, RedirectAttributes attributes,HttpSession session){
        //获取 user
        blog.setUser((User) session.getAttribute("user"));

        //获取 type tags
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));


        //获取 typeId userId
        blog.setTypeId(blog.getType().getId());
        blog.setUserId(blog.getUser().getId());

        int b1 = blogService.saveBlog(blog);

        if (b1 == 0){
            attributes.addFlashAttribute("message","操作失败！");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }

        return "redirect:/admin/blogs";
    }

    /* 删除 */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlogById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

}
