package com.jack.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jack.admin.pojo.PurchaseList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.admin.query.PurchaseListQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 进货单 Mapper 接口
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface PurchaseListMapper extends BaseMapper<PurchaseList> {

    String getNextPurchaseNumber();

    IPage<PurchaseList> purchaseList(IPage<PurchaseList> page, @Param("purchaseListQuery") PurchaseListQuery purchaseListQuery);
}
