package com.jack.admin.controller;


import com.jack.admin.exceptions.ParamsException;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.User;
import com.jack.admin.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;

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

    @RequestMapping("login")
    @ResponseBody // return json
    public RespBean login(String userName, String password, HttpSession session){
            User user = userService.login(userName, password);
            session.setAttribute("user", user);
            return RespBean.success("用户登录成功");
    }

    /**
     * 显示用户信息设置界面
     *
     * @param session
     * @return
     */
    @RequestMapping("setting")
    public String setting(HttpSession session){
        User user = (User) session.getAttribute("user");
        session.setAttribute("user", userService.getById(user.getId()));
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
     * @param session
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @RequestMapping("updateUserPassword")
    @ResponseBody
    public RespBean updateUserPassword(HttpSession session, String oldPassword,String newPassword,String confirmPassword){
            User user = (User) session.getAttribute("user");
            userService.updateUserPassword(user.getUserName(), oldPassword, newPassword, confirmPassword);
            return RespBean.success("用户密码更新成功");
    }


}
