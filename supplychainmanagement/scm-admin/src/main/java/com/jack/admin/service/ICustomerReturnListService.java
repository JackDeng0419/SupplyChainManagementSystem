package com.jack.admin.service;

import com.jack.admin.pojo.CustomerReturnList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.CustomerReturnListGoods;
import com.jack.admin.query.CustomerReturnListQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户退货单表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-13
 */
public interface ICustomerReturnListService extends IService<CustomerReturnList> {

    String getNextCustomerReturnNumber();

    void saveCustomerReturnList(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> crlgList);

    Map<String, Object> customerReturnList(CustomerReturnListQuery customerReturnListQuery);
}
