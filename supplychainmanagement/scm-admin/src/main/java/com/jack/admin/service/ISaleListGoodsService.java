package com.jack.admin.service;

import com.jack.admin.pojo.SaleListGoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 销售单商品表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface ISaleListGoodsService extends IService<SaleListGoods> {

    Integer getSaleTotalByGoodsId(Integer id);
}
