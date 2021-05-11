package com.jack.admin.controller;


import com.jack.admin.dto.TreeDto;
import com.jack.admin.service.IMenuService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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


}
