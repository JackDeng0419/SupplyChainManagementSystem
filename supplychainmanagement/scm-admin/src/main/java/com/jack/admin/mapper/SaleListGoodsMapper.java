package com.jack.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jack.admin.pojo.SaleListGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.admin.query.SaleListGoodsQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 销售单商品表 Mapper 接口
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface SaleListGoodsMapper extends BaseMapper<SaleListGoods> {

    IPage<SaleListGoods> saleListGoodsList(IPage<SaleListGoods> page, @Param("saleListGoodsQuery") SaleListGoodsQuery saleListGoodsQuery);
}
