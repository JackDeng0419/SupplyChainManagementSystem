package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.admin.pojo.User;
import com.jack.admin.mapper.UserMapper;
import com.jack.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public User login(String userName, String password) {
        AssertUtil.isTrue(StringUtil.isEmpty(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtil.isEmpty(password), "密码不能为空");
        User user = this.findUserByUserName(userName);
        AssertUtil.isTrue(null == user, "该用户记录不存在或已注销");
        /**
         * 后续：使用加密代码比对, using spring security
         */
        AssertUtil.isTrue(!(user.getPassword().equals(password)), "密码错误");
        return user;
    }

    @Override
    public User findUserByUserName(String userName) {
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("is_del",0).eq("user_name", userName));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // 只要抛出异常便回滚
    public void updateUserInfo(User user) {
        //1. 账号是否为空
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUserName()), "用户名不能为空");

        //2. 判断账号是否唯一
        User temp = findUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "用户名已存在");

        //3. 更新信息, 直接调用父类（MyBatis Plus的ServiceImpl）的方法
        AssertUtil.isTrue(!(this.updateById(user)), "用户信息更新失败");
    }
}
