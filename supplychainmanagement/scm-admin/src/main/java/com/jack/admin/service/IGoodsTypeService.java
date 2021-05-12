package com.jack.admin.service;

import com.jack.admin.dto.TreeDto;
import com.jack.admin.pojo.GoodsType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品类别表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface IGoodsTypeService extends IService<GoodsType> {

    List<Integer> queryAllSubTypeIdsByTypeId(Integer typeId);

    List<TreeDto> queryAllGoodsTypes(Integer typeId);
}
