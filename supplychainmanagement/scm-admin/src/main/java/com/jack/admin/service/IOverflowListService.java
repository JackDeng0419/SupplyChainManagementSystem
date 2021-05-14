package com.jack.admin.service;

import com.jack.admin.pojo.OverflowList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.OverflowListGoods;
import com.jack.admin.query.OverFlowListQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报溢单表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
public interface IOverflowListService extends IService<OverflowList> {

    String getNextOverflowNumber();

    void saveOverflowList(OverflowList overflowList, List<OverflowListGoods> oflList);

    Map<String, Object> overFlowList(OverFlowListQuery overFlowListQuery);
}
