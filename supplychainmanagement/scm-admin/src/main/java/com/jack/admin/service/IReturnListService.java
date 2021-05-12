package com.jack.admin.service;

import com.jack.admin.pojo.ReturnList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.ReturnListGoods;
import com.jack.admin.query.ReturnListQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 退货单表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface IReturnListService extends IService<ReturnList> {

    String getNextReturnNumber();

    void saveReturnList(ReturnList returnList, List<ReturnListGoods> rlgList);

    Map<String, Object> returnList(ReturnListQuery returnListQuery);

    void deleteReturnList(Integer id);
}
