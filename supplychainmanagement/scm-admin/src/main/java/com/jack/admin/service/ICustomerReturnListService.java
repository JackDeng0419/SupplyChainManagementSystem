package com.jack.admin.service;

import com.jack.admin.pojo.CustomerReturnList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.CustomerReturnListGoods;

import java.util.List;

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
}
