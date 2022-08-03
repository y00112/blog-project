package com.wukong.web;

import com.wukong.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created By WuKong on 2022/7/22 16:02
 **/
@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public  String archives(Model model) {
        model.addAttribute("archivesMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());

        return "archives";
    }
}
