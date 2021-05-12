package com.jack.admin.service;

import com.jack.admin.pojo.PurchaseListGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.PurchaseListGoodsQuery;

import java.util.Map;

/**
 * <p>
 * 进货单商品表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface IPurchaseListGoodsService extends IService<PurchaseListGoods> {

    Map<String, Object> purchaseListGoodsList(PurchaseListGoodsQuery purchaseListGoodsQuery);
}
