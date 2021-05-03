package com.jack.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /**
     * 系统主页面
     */
    @RequestMapping("main")
    public String main(){
        return "main";
    }

    /**
     * 系统欢迎页
     */
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 用户退出
     * @return
     */
    @RequestMapping("signout")
    public String signout(HttpSession session){
        session.removeAttribute("user");
        //如果不写redirect，地址栏处仍然会是/signout
        return "redirect:index";
    }
}
