package com.jack.admin.service;

import com.jack.admin.pojo.PurchaseList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.PurchaseListGoods;
import com.jack.admin.query.PurchaseListQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进货单 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface IPurchaseListService extends IService<PurchaseList> {

    String getNextPurchaseNumber();

    void savePurchaseList(PurchaseList purchaseList, List<PurchaseListGoods> plgList);

    Map<String, Object> purchaseList(PurchaseListQuery purchaseListQuery);

    void deletePurchaseList(Integer id);
}
