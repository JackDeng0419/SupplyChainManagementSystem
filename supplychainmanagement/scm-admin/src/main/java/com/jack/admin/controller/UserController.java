package com.jack.admin.controller;


import com.jack.admin.exceptions.ParamsException;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.User;
import com.jack.admin.query.UserQuery;
import com.jack.admin.service.IUserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;
import java.security.Principal;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-04-30
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

//    @RequestMapping("login")
//    @ResponseBody // return json
//    public RespBean login(String userName, String password, HttpSession session){
//            User user = userService.login(userName, password);
//            session.setAttribute("user", user);
//            return RespBean.success("用户登录成功");
//    }

    /**
     * 显示用户信息设置界面
     * @param principal
     * @param model
     * @return
     */
    @RequestMapping("setting")
    //@PreAuthorize("hasAnyAuthority('1010')")
    public String setting(Principal principal, Model model){
        User user = userService.findUserByUserName(principal.getName());
        model.addAttribute("user",user);
        return "user/setting";
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @RequestMapping("updateUserInfo")
    @ResponseBody
    public RespBean updateUserInfo(User user){
            userService.updateUserInfo(user);
            return RespBean.success("更新成功");
    }

    /**
     * 显示密码修改界面
     * @return
     */
    @RequestMapping("password")
    public String password(){
//        User user = (User) session.getAttribute("user");
//        session.setAttribute("user", userService.getById(user.getId()));
        return "user/password";
    }

    /**
     * 修改用户密码
     * @param principal
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @RequestMapping("updateUserPassword")
    @ResponseBody
    public RespBean updateUserPassword(Principal principal, String oldPassword, String newPassword, String confirmPassword){
            userService.updateUserPassword(principal.getName(), oldPassword, newPassword, confirmPassword);
            return RespBean.success("用户密码更新成功");
    }

    @RequestMapping("index")
    //@PreAuthorize("hasAnyAuthority(1010)")
    public String index(){
        return "user/user";
    }


    /**
     * 查询用户列表
     * @param userQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    //@PreAuthorize("hasAnyAuthority('101003')")
    public Map<String, Object> userList(UserQuery userQuery){
        return userService.userList(userQuery);
    }

    @RequestMapping("addOrUpdateUserPage")
    //@PreAuthorize("hasAnyAuthority('101004','101005')")
    public String addOrUpdatePage(Integer id, Model model){
        if (null != id){
            // 将user对象放入session
            model.addAttribute("user",userService.getById(id));
        }
        return  "user/add_update";
    }

    /**
     * 新增用户
     *
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveUser(User user){
        userService.saveUser(user);
        return RespBean.success("用户记录添加成功");
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateUser(User user){
        userService.updateUser(user);
        return RespBean.success("用户记录更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteUser(Integer[] ids){
        userService.deleteUser(ids);
        return RespBean.success("用户记录删除成功");

    }
}
