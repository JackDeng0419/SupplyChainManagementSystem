package com.jack.admin.service;

import com.jack.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-04-30
 */
public interface IUserService extends IService<User> {

    User login(String userName, String password);

    /**
     * 根据用户名查询数据
     * @param userName
     * @return
     */
    public User findUserByUserName(String userName);
}
