package com.wukong.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.dto.BlogQuery;
import com.wukong.exception.MyNotFoundException;
import com.wukong.pojo.Blog;
import com.wukong.pojo.Tag;
import com.wukong.pojo.Type;
import com.wukong.service.BlogService;
import com.wukong.service.TagService;
import com.wukong.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//首页
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /*首页*/
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        Model model){
        //获取bolg列表
        PageHelper.startPage(pageNum,4);
        List<Blog> blogs = blogService.selectAllBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);

        //获取type列表
        List<Type> types = typeService.findTypeAndBlogAll();
        model.addAttribute("types",types);

        //获取tag列表
        List<Tag> tags = tagService.listTag();
        model.addAttribute("tags",tags);

        //获取推荐列表
        List<Blog> blogRecommend = blogService.listRecommendBlogTop();
        model.addAttribute("recommendBlogs",blogRecommend);

        System.out.println("--------------index-------------");
        return "index";

    }

    /*blog */
    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

    /* 全局搜索 */
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         @RequestParam String query,
                         Model model){
        PageHelper.startPage(pageNum,4);
        List<Blog> blogs = blogService.listBolg(query);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);

        return "search";
    }

    /**
     * 博客详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){

        Blog blog = blogService.getAndConvertBlog(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

    /**
     * 关于我页面
     * @param model
     * @return
     */
    @GetMapping("/footer/newblogs")
    public String newBlogs(Model model) {

        model.addAttribute("newblogs",blogService.listRecommendBlogTop());

        System.out.println("===================================");

        return "_fragments :: newbloglist";
    }
}
