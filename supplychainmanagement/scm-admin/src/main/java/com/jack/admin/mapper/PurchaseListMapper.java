package com.jack.admin.mapper;

import com.jack.admin.pojo.PurchaseList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
