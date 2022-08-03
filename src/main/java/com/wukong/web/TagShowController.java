package com.wukong.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created By WuKong on 2022/7/22 14:21
 **/
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @PathVariable Long id,
                        Model model) {

        List<Tag> tags = tagService.listTag();

        if (id == -1){
            id = tags.get(0).getId();
        }

        PageHelper.startPage(pageNum,4);
        List<Blog> blogs = blogService.findBlogByTag(id);

        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("tags",tags);

        model.addAttribute("activeTypeId",id);

        return "tags";
    }
}
