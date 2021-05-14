package com.jack.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jack.admin.pojo.OverflowList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.admin.query.OverFlowListQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 报溢单表 Mapper 接口
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
public interface OverflowListMapper extends BaseMapper<OverflowList> {


    String getNextOverflowNumber();

    IPage<OverflowList> overFlowList(IPage<OverflowList> page, @Param("overFlowListQuery") OverFlowListQuery overFlowListQuery);
}
