package com.jack.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jack.admin.pojo.SaleList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.admin.query.SaleListQuery;
import org.apache.ibatis.annotations.Param;

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

    IPage<SaleList> saleList(IPage<SaleList> page, @Param("saleListQuery") SaleListQuery saleListQuery);
}
