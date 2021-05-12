package com.jack.admin.service;

import com.jack.admin.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.GoodsQuery;

import java.util.Map;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface IGoodsService extends IService<Goods> {

    void saveGoods(Goods goods);

    void updateGoods(Goods goods);

    void deleteGoods(Integer id);

    Map<String, Object> goodsList(GoodsQuery goodsQuery);

    String genGoodsCode();

    void updateStock(Goods goods);

    void deleteStock(Integer id);

    Goods getGoodsInfoById(Integer gid);
}
