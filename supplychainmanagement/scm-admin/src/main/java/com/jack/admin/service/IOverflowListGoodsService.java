package com.jack.admin.service;

import com.jack.admin.pojo.OverflowListGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.OverflowListGoodsQuery;

import java.util.Map;

/**
 * <p>
 * 报溢单商品表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
public interface IOverflowListGoodsService extends IService<OverflowListGoods> {

    Map<String, Object> overflowListGoodsList(OverflowListGoodsQuery overflowListGoodsQuery);
}
