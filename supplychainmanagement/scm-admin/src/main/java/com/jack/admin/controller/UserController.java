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

        try {
            User user = userService.login(userName, password);
            session.setAttribute("user", user);
            return RespBean.success("用户登录成功");
        } catch (ParamsException pe) {
            pe.printStackTrace();
            return RespBean.error(pe.getMsg());
        } catch (Exception exception) {
            exception.printStackTrace();
            return RespBean.error("用户登陆失败");
        }
    }

    /**
     * 用户信息设置
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

    @RequestMapping("updateUserInfo")
    @ResponseBody
    public RespBean updateUserInfo(User user){
        try {
            userService.updateUserInfo(user);
            return RespBean.success("更新成功");
        } catch (ParamsException exception) {
            exception.printStackTrace();
            return RespBean.error(exception.getMsg());
        } catch (Exception exception) {
            exception.printStackTrace();
            return RespBean.error("用户更新失败");
        }
    }
}