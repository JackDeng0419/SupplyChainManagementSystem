package com.jack.admin.service;

import com.jack.admin.pojo.PurchaseList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.PurchaseListGoods;

import java.util.List;

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
}
