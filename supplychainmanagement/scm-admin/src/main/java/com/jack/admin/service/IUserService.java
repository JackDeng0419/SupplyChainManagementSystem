package com.jack.admin.service;

import com.jack.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.UserQuery;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-04-30
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名查询数据
     * @param userName
     * @return
     */
    public User findUserByUserName(String userName);

    void updateUserInfo(User user);

    void updateUserPassword(String userName, String oldPassword, String newPassword, String confirmPassword);

    Map<String, Object> userList(UserQuery userQuery);
}
