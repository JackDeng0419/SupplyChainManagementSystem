package com.jack.admin.controller;

import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.Role;
import com.jack.admin.query.RoleQuery;
import com.jack.admin.service.IRoleService;
import com.jack.admin.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("role")
@Controller
public class RoleController {
    @Autowired
    IRoleService roleService;

    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdatePage(Integer id, Model model){
        if(null != id){
            model.addAttribute("role", roleService.getById(id));
        }
        return "role/add_update";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> roleList(RoleQuery roleQuery){
        return roleService.roleList(roleQuery);
    }

    @RequestMapping("update")
    @ResponseBody
    public RespBean updateRole(Role role){
        roleService.updateRole(role);
        return RespBean.success("角色更新成功");
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean saveRole(Role role){
        roleService.saveRole(role);
        return RespBean.success("角色创建成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteRole(Integer id){
        roleService.deleteRole(id);
        return RespBean.success("角色删除成功");
    }

}
