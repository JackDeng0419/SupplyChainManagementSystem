package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jack.admin.pojo.Menu;
import com.jack.admin.pojo.Supplier;
import com.jack.admin.mapper.SupplierMapper;
import com.jack.admin.query.SupplierQuery;
import com.jack.admin.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.PageResultUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSupplier(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length==0,"请选择待删除记录id");
        List<Supplier> supplierList =new ArrayList<Supplier>();
        for (Integer id : ids) {
            Supplier temp =this.getById(id);
            temp.setIsDel(1);
            supplierList.add(temp);
        }
        AssertUtil.isTrue(!(this.updateBatchById(supplierList)),"记录删除失败!");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveSupplier(Supplier supplier) {
        //非空校验
        checkParams(supplier.getName(), supplier.getContact(), supplier.getNumber());
        //无重复名称
        AssertUtil.isTrue(null != this.findSupplierByName(supplier.getName()), "供应商已存在");
        // 设置is_del=0
        supplier.setIsDel(0);
        //保存supplier
        AssertUtil.isTrue(!(this.save(supplier)), "记录添加失败");
    }

    @Override
    public Supplier findSupplierByName(String name) {
        return this.getOne(new QueryWrapper<Supplier>().eq("is_del", 0).eq("name", name));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSupplier(Supplier supplier) {
        // 非空校验
        checkParams(supplier.getName(), supplier.getContact(), supplier.getNumber());
        // name不能重复（查询此名字为空或id等于自己）
        Supplier temp = this.findSupplierByName(supplier.getName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(supplier.getId())),"供应商已存在!");
        // 更新
        AssertUtil.isTrue(!(this.updateById(supplier)),"记录更新失败!");
    }

    private void checkParams(String name, String contact, String number) {
        AssertUtil.isTrue(StringUtil.isEmpty(name),"请输入供应商名称!");
        AssertUtil.isTrue(StringUtil.isEmpty(contact),"请输入联系人!");
        AssertUtil.isTrue(StringUtil.isEmpty(number),"请输入联系电话!");
    }
}
