package com.jack.admin.service;

import com.jack.admin.dto.TreeDto;
import com.jack.admin.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-11
 */
public interface IMenuService extends IService<Menu> {

    List<TreeDto> queryAllMenus(Integer roleId);
}
