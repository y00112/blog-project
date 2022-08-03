package com.wukong.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.pojo.Tag;
import com.wukong.service.TagService;
import com.wukong.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created By WuKong on 2022/7/11 17:48
 **/
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询标签列表
     * 分页
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                        Model model){
        PageHelper.startPage(pageNum,5);
        List<Tag> list = tagService.listTag();
        PageInfo<Tag> pageInfo = new PageInfo<>(list);
        model.addAttribute("page",pageInfo);

        return "admin/tags";
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     * 新增分类
     * @param tag
     * @return
     */
    @PostMapping("/tags")
    public String save(@Valid Tag tag,BindingResult result, RedirectAttributes attributes){
        //后端非空校验
        if (result.hasErrors()){
            return "admin/tags-input";
        }

        //是否有重复name
        Tag t1 = tagService.getTagByName(tag.getName());
        if (null != t1){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }

        //新增操作
        int t = tagService.saveTag(tag);
        if (t == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        //重定向
        return "redirect:/admin/tags";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        Tag t1 = tagService.getTag(id);
        model.addAttribute("tag",t1);
        return "admin/tags-input";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        //后端非空校验
        if (result.hasErrors()){
            return "admin/tags-input";
        }

        //是否有重复name
        Tag t1 = tagService.getTagByName(tag.getName());
        if (null != t1){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }

        //更新操作
        int t = tagService.updateTag(id,tag);
        if (t == 0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        //重定向
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}