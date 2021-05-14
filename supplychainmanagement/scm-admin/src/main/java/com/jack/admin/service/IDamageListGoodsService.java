package com.jack.admin.service;

import com.jack.admin.pojo.DamageListGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.DamageListGoodsQuery;

import java.util.Map;

/**
 * <p>
 * 报损单商品表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
public interface IDamageListGoodsService extends IService<DamageListGoods> {

    Map<String, Object> damageListGoodsList(DamageListGoodsQuery damageListGoodsQuery);
}
