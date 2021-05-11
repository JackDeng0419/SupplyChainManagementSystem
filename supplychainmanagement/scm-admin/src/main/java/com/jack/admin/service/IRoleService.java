package com.jack.admin.service;

import com.jack.admin.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.RoleQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-10
 */
public interface IRoleService extends IService<Role> {

    Role findRoleByRoleName(String roleName);

    Map<String, Object> roleList(RoleQuery roleQuery);

    void updateRole(Role role);

    void saveRole(Role role);

    void deleteRole(Integer id);

    List<Map<String,Object>> queryAllRoles(Integer userId);

    void addGrant(Integer roleId, Integer[] mids);
}
