package com.jack.admin.service;

import com.jack.admin.pojo.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.SupplierQuery;

import java.util.Map;

/**
 * <p>
 * 供应商表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface ISupplierService extends IService<Supplier> {

    Map<String, Object> supplierList(SupplierQuery supplierQuery);

    void deleteSupplier(Integer[] ids);

    void saveSupplier(Supplier supplier);

    Object findSupplierByName(String name);

    void updateSupplier(Supplier supplier);
}
