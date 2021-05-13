package com.jack.admin.mapper;

import com.jack.admin.pojo.SaleList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 销售单表 Mapper 接口
 * </p>
 *
 * @author Jack
 * @since 2021-05-13
 */
public interface SaleListMapper extends BaseMapper<SaleList> {

    String getNextSaleNumber();
}
