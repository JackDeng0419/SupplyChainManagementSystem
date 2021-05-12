package com.jack.admin.service;

import com.jack.admin.pojo.ReturnListGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.ReturnListGoodsQuery;

import java.util.Map;

/**
 * <p>
 * 退货单商品表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface IReturnListGoodsService extends IService<ReturnListGoods> {

    Map<String, Object> returnListGoodsList(ReturnListGoodsQuery returnListGoodsQuery);
}
