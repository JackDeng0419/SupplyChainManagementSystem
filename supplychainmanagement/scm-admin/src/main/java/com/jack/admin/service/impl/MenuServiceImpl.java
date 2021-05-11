package com.jack.admin.service.impl;

import com.jack.admin.dto.TreeDto;
import com.jack.admin.pojo.Menu;
import com.jack.admin.mapper.MenuMapper;
import com.jack.admin.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-11
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<TreeDto> queryAllMenus(Integer roleId) {
        List<TreeDto> treeDtos = this.baseMapper.queryAllMenus();

        return treeDtos;
    }

//    @Override
//    public
}
