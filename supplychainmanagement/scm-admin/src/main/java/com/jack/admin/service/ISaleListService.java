package com.jack.admin.service;

import com.jack.admin.model.RespBean;
import com.jack.admin.model.SaleCount;
import com.jack.admin.pojo.SaleList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.SaleListGoods;
import com.jack.admin.query.SaleListQuery;

import java.util.List;
import java.util.Map;

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

    Map<String, Object> saveList(SaleListQuery saleListQuery);

    Map<String, Object> countSale(SaleListQuery saleListQuery);

    List<Map<String,Object>> countDaySale(String begin, String end);

    List<Map<String, Object>> countMonthSale(String begin, String end);
}
