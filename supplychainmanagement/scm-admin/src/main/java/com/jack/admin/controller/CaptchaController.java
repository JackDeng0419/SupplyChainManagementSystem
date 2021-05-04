package com.jack.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jack.admin.model.CaptchaImageModel;

@RestController
public class CaptchaController {

    @Resource
    public DefaultKaptcha defaultKaptcha;

    /**
     * 其实只是一个普通的图片响应方法，只不过用了defaultKaptcha来产生验证码图片而已
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void kaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        // 设置图片返回的响应头
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 获得验证码文字
        String capText = defaultKaptcha.createText();

        // 将验证码存入session, 并设置两分钟后过期
        session.setAttribute("captcha_key",new CaptchaImageModel(capText,2*60));

        // 返回图片
        ServletOutputStream out = response.getOutputStream(); // 获取response输出流
        BufferedImage bufferedImage = defaultKaptcha.createImage(capText); // 图片的缓存形式
        ImageIO.write(bufferedImage, "jpg", out); // 把图片缓存写进输出流对象
        out.flush(); // 输出给客户端
    }
}
