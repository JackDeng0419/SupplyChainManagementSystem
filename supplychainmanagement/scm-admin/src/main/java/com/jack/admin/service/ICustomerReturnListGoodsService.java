package com.jack.admin.service;

import com.jack.admin.pojo.CustomerReturnListGoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 客户退货单商品表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface ICustomerReturnListGoodsService extends IService<CustomerReturnListGoods> {

    Integer getReturnTotalByGoodsId(Integer id);
}
