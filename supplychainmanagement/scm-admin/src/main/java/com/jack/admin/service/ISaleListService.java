package com.jack.admin.service;

import com.jack.admin.pojo.SaleList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.SaleListGoods;

import java.util.List;

/**
 * <p>
 * 销售单表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-13
 */
public interface ISaleListService extends IService<SaleList> {

    String getNextSaleNumber();

    void saveSaleList(SaleList saleList, List<SaleListGoods> slgList);
}
