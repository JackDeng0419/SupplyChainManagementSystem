package com.jack.admin.service;

import java.util.List;

public interface IRbacService {

    //根据用户名查询角色，返回角色名列表
    List<String> findRolesByUserName(String userName);

    //根据角色名查询权限，返回权限名列表
    List<String> findAuthoritiesByRoleName(List<String> roleNames);
}
