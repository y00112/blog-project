package com.wukong.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.pojo.Type;
import com.wukong.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 查询分类列表
     * 分页
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                        Model model){
        PageHelper.startPage(pageNum,5);
        List<Type> list = typeService.listType();
        PageInfo<Type> pageInfo = new PageInfo<>(list);
        model.addAttribute("page",pageInfo);

        return "admin/types";
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
     * 新增分类
     * @param type
     * @return
     */
    @PostMapping("/types")
    public String save(@Valid Type type,BindingResult result, RedirectAttributes attributes){
        //后端非空校验
        if (result.hasErrors()){
            return "admin/types-input";
        }

        //是否有重复name
        Type t1 = typeService.getTypeByName(type.getName());
        if (null != t1){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }

        //新增操作
        int t = typeService.saveType(type);
        if (t == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        //重定向
        return "redirect:/admin/types";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        Type t1 = typeService.getType(id);
        model.addAttribute("type",t1);
        return "admin/types-input";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        //后端非空校验
        if (result.hasErrors()){
            return "admin/types-input";
        }

        //是否有重复name
        Type t1 = typeService.getTypeByName(type.getName());
        if (null != t1){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }

        //更新操作
        int t = typeService.updateType(id,type);
        if (t == 0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        //重定向
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}