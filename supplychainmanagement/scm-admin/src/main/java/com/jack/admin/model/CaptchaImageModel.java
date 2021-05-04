package com.jack.admin.model;

import java.time.LocalDateTime;

public class CaptchaImageModel {
    private String code;

    private LocalDateTime expireTime;

    /**
     * 构造方法
     */
    public CaptchaImageModel(String code, int expireAfterSeconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireAfterSeconds);
    }

    public String getCode(){
        return code;
    }

    /**
     * 判断验证码是否已经失效
     * @return
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
