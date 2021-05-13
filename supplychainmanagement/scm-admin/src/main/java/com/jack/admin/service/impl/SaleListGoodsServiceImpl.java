package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.SaleListGoods;
import com.jack.admin.mapper.SaleListGoodsMapper;
import com.jack.admin.query.SaleListGoodsQuery;
import com.jack.admin.service.ISaleListGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.PageResultUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 销售单商品表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Service
public class SaleListGoodsServiceImpl extends ServiceImpl<SaleListGoodsMapper, SaleListGoods> implements ISaleListGoodsService {

    @Override
    public Integer getSaleTotalByGoodsId(Integer id) {
        SaleListGoods saleListGoods = this.getOne(new QueryWrapper<SaleListGoods>().select("sum(num) as num").eq("goods_id",id));
        return null == saleListGoods?0:saleListGoods.getNum();
    }

    @Override
    public Map<String, Object> saleListGoodsList(SaleListGoodsQuery saleListGoodsQuery) {
        IPage<SaleListGoods> page = new Page<SaleListGoods>(saleListGoodsQuery.getPage(), saleListGoodsQuery.getLimit());
        page = this.baseMapper.saleListGoodsList(page, saleListGoodsQuery);
        return PageResultUtil.getResult(page.getSize(), page.getRecords());
    }
}
