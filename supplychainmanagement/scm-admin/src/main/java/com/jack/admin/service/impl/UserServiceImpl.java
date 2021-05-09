package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jack.admin.pojo.User;
import com.jack.admin.mapper.UserMapper;
import com.jack.admin.query.UserQuery;
import com.jack.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-04-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private PasswordEncoder encoder;

    @Override
    public User findUserByUserName(String userName) {
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("is_del", 0).eq("user_name", userName));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // 只要抛出异常便回滚
    public void updateUserInfo(User user) {
        //1. 账号是否为空
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUsername()), "用户名不能为空");

        //2. 判断账号是否唯一
        User temp = findUserByUserName(user.getUsername());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "用户名已存在");

        //3. 更新信息, 直接调用父类（MyBatis Plus的ServiceImpl）的方法
        AssertUtil.isTrue(!(this.updateById(user)), "用户信息更新失败");
    }

    @Override
    public void updateUserPassword(String userName, String oldPassword, String newPassword, String confirmPassword) {
        //非空检验
        User user = null;
        user = this.findUserByUserName(userName);
        AssertUtil.isTrue(null==user, "此用户不存在或未登录");
        AssertUtil.isTrue(StringUtil.isEmpty(oldPassword), "旧密码不能为空");
        AssertUtil.isTrue(StringUtil.isEmpty(newPassword), "新密码不能为空");
        //错误判断
        AssertUtil.isTrue(StringUtil.isEmpty(confirmPassword),"请输入确认密码!");
        AssertUtil.isTrue(!(encoder.matches(oldPassword, user.getPassword())),"原始密码输入错误!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码输入不一致!");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码与原始密码不能一致!");

        //更新密码, 直接调用父类（MyBatis Plus的ServiceImpl）的方法
        user.setPassword(encoder.encode(newPassword));
        AssertUtil.isTrue(!(this.updateById(user)), "用户密码更新失败");
    }

    @Override
    public Map<String, Object> userList(UserQuery userQuery) {
        // 构建页面数据结构
        IPage<User> page = new Page<User>(userQuery.getPage(), userQuery.getLimit());
        // 创建查询数据结构
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        // 只查询有效的用户
        queryWrapper.eq("is_del",0);
        //
        if(!StringUtil.isEmpty(userQuery.getUserName())){
            queryWrapper.like("user_name", userQuery.getUserName());
        }

        page = this.baseMapper.selectPage(page, queryWrapper);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",0);
        map.put("msg","");
        map.put("data", page.getRecords());
        map.put("count",page.getTotal());

        return map;
    }
}
