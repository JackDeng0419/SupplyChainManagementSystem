package com.jack.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jack.admin.pojo.ReturnList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.admin.query.ReturnListQuery;

/**
 * <p>
 * 退货单表 Mapper 接口
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface ReturnListMapper extends BaseMapper<ReturnList> {

    String getNextPurchaseNumber();

    IPage<ReturnList> returnList(IPage<ReturnList> page, ReturnListQuery returnListQuery);
}
