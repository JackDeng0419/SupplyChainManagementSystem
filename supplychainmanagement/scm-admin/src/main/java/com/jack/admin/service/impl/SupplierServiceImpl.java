package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.Menu;
import com.jack.admin.pojo.Supplier;
import com.jack.admin.mapper.SupplierMapper;
import com.jack.admin.query.SupplierQuery;
import com.jack.admin.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.PageResultUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {

    @Override
    public Map<String, Object> supplierList(SupplierQuery supplierQuery) {
        //创建页面对象
        IPage<Supplier> page = new Page<Supplier>(supplierQuery.getPage(), supplierQuery.getLimit());
        // 创建查询对象
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<Supplier>().eq("is_del", 0);

        //根据查询对象与页面对象查询数据库
        page = this.baseMapper.selectPage(page, queryWrapper);
        return PageResultUtil.getResult(page.getTotal(), page.getRecords());
    }
}
