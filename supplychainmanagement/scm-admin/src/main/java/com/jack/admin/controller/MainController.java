package com.jack.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
