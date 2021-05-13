package com.jack.admin.mapper;

import com.jack.admin.pojo.CustomerReturnList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 客户退货单表 Mapper 接口
 * </p>
 *
 * @author Jack
 * @since 2021-05-13
 */
public interface CustomerReturnListMapper extends BaseMapper<CustomerReturnList> {

    String getNextCustomerReturnNumber();
}
