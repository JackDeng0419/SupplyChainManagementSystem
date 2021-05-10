package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.Role;
import com.jack.admin.mapper.RoleMapper;
import com.jack.admin.pojo.User;
import com.jack.admin.query.RoleQuery;
import com.jack.admin.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Override
    public Role findRoleByRoleName(String roleName) {
        return this.baseMapper.selectOne(new QueryWrapper<Role>().eq("is_del", 0).eq("name",roleName));
    }

    @Override
    public Map<String, Object> roleList(RoleQuery roleQuery) {
        // 构建页面数据结构
        IPage<Role> page = new Page<Role>(roleQuery.getPage(), roleQuery.getLimit());
        // 构建查询数据结构
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        // 只查询有效角色
        queryWrapper.eq("is_del", 0);

        //如果有值，则模糊查询
        if(!StringUtil.isEmpty(roleQuery.getRoleName())){
            queryWrapper.like("name", roleQuery.getRoleName());
        }

        page = this.baseMapper.selectPage(page, queryWrapper);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("data", page.getRecords());
        map.put("count", page.getTotal());
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateRole(Role role) {
        // 非空判断
        AssertUtil.isTrue( StringUtil.isEmpty(role.getName()),"请输入角色名");
        // 无重复角色名
        Role temp = this.findRoleByRoleName(role.getName());
        AssertUtil.isTrue(null!=temp&&!(temp.getId().equals(role.getId())), "角色名已存在");
        AssertUtil.isTrue(!(this.updateById(role)), "用户更新失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveRole(Role role) {
        // 非空判断
        AssertUtil.isTrue( StringUtil.isEmpty(role.getName()),"请输入角色名");
        // 无重复角色名
        AssertUtil.isTrue(null!=this.findRoleByRoleName(role.getName()), "角色名已存在");
        role.setIsDel(0);
        AssertUtil.isTrue(!(this.save(role)), "用户创建失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteRole(Integer id) {
        AssertUtil.isTrue(null == id, "请选择待删除记录");
        Role role = this.getById(id);
        AssertUtil.isTrue(null == role, "待删除记录不存在");
        role.setIsDel(1);
        AssertUtil.isTrue(!(this.updateById(role)),"用户记录删除失败");
    }
}
