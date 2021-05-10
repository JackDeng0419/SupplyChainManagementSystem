package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.User;
import com.jack.admin.mapper.UserMapper;
import com.jack.admin.pojo.UserRole;
import com.jack.admin.query.UserQuery;
import com.jack.admin.service.IUserRoleService;
import com.jack.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    private PasswordEncoder passwordEncoder;

    @Resource
    private IUserRoleService userRoleService;

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUserPassword(String userName, String oldPassword, String newPassword, String confirmPassword) {
        //非空检验
        User user = null;
        user = this.findUserByUserName(userName);
        AssertUtil.isTrue(null==user, "此用户不存在或未登录");
        AssertUtil.isTrue(StringUtil.isEmpty(oldPassword), "旧密码不能为空");
        AssertUtil.isTrue(StringUtil.isEmpty(newPassword), "新密码不能为空");
        //错误判断
        AssertUtil.isTrue(StringUtil.isEmpty(confirmPassword),"请输入确认密码!");
        AssertUtil.isTrue(!(passwordEncoder.matches(oldPassword, user.getPassword())),"原始密码输入错误!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码输入不一致!");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码与原始密码不能一致!");

        //更新密码, 直接调用父类（MyBatis Plus的ServiceImpl）的方法
        user.setPassword(passwordEncoder.encode(newPassword));
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUser(User user) {
        // 校验
        //  username非空，不可重复
        // 设置默认值
            //密码：123456
            //is_del=0
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUsername()), "用户名不能为空");
        AssertUtil.isTrue(null != this.findUserByUserName(user.getUsername()), "用户名已存在");

        user.setPassword(passwordEncoder.encode("123456"));
        user.setIsDel(0);
        AssertUtil.isTrue(!(this.save(user)), "用户记录添加失败");

        //重新获取用户记录
        User temp = this.findUserByUserName(user.getUsername());
        //分配角色
        relationUserRole(temp.getId(), user.getRoleIds());

    }

    /**
     * 将用户ID与角色ID关联
     * @param userId
     * @param roleIds
     */
    private void relationUserRole(Integer userId, String roleIds) {
        /**
         * 当添加用户时
         *      直接添加
         * 当更新角色时
         *      如果用户存在原始的角色，先把原始角色记录删除，再重新添加
         *      如果不存在，则直接添加
         */

        // 先查询此用户id在user_role中是否有记录
        int count = userRoleService.count(new QueryWrapper<UserRole>().eq("user_id", userId));
        if(count>0){
            AssertUtil.isTrue(!(userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId))), "用户角色分配失败");
        }
        if(StringUtil.isNotEmpty(roleIds)){
            List<UserRole> userRoles = new ArrayList<UserRole>();
            for(String s:roleIds.split(",")) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(Integer.parseInt(s));
                userRoles.add(userRole);
            }
            AssertUtil.isTrue(!(userRoleService.saveBatch(userRoles)),"用户角色分配失败");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUser(User user) {
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUsername()), "用户名不能为空");

        User temp = this.findUserByUserName(user.getUsername());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "用户名已存在");

        relationUserRole(user.getId(), user.getRoleIds());

        AssertUtil.isTrue(!(this.updateById(user)), "用户记录更新失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUser(Integer[] ids) {
        // 判断参数非空
        AssertUtil.isTrue(null == ids || ids.length==0, "请选择删除的记录id");

        // 删除user_role中此userID的记录
        int count = userRoleService.count(new QueryWrapper<UserRole>().in("user_id", Arrays.asList(ids))); // 将ids转为List
        if(count>0){
            AssertUtil.isTrue(!(userRoleService.remove(new QueryWrapper<UserRole>().in("user_id", Arrays.asList(ids)))),"用户删除失败");
        }

        //删除user表
        List<User> users = new ArrayList<User>();
        for(Integer id : ids){
            User temp = this.getById(id);
            temp.setIsDel(1);
            users.add(temp);
        }
        AssertUtil.isTrue(!(this.updateBatchById(users)), "用户记录删除失败");

    }
}
