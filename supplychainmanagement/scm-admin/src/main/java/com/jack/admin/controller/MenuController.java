package com.jack.admin.controller;


import com.jack.admin.dto.TreeDto;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.Menu;
import com.jack.admin.service.IMenuService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-11
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @RequestMapping("queryAllMenus")
    @ResponseBody
    public List<TreeDto> queryAllMenus(Integer roleId) {
        return menuService.queryAllMenus(roleId);
    }

    @RequestMapping("index")
    public String index(){
        return "menu/menu";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> menuList() {
        return menuService.menuList();
    }

//    @RequestMapping("delete")
//    public String menuList() {
//        return ;
//    }

    @RequestMapping("addMenuPage")
    public String addMenuPage(Integer grade, Integer pId, Model model) {
        model.addAttribute("grade", grade);
        model.addAttribute("pId", pId);
        return "menu/add";
    }

    @RequestMapping("updateMenuPage")
    public String updateMenuPage(Integer id, Model model) {
        model.addAttribute("menu", menuService.getById(id));
        return "menu/update";
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return RespBean.success("菜单记录添加成功!");
    }

    @RequestMapping("update")
    @ResponseBody
    public RespBean updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return RespBean.success("菜单记录更新成功!");
    }

    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteMenu(Integer id){
        menuService.deleteMenuById(id);
        return RespBean.success("菜单删除成功");
    }
}
