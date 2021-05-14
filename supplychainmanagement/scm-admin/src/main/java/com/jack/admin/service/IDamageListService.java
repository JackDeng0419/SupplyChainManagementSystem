package com.jack.admin.service;

import com.jack.admin.pojo.DamageList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.pojo.DamageListGoods;
import com.jack.admin.query.DamageListQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报损单表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
public interface IDamageListService extends IService<DamageList> {

    Object getNextDamageNumber();

    void saveDamageList(DamageList damageList, List<DamageListGoods> plgList);

    Map<String, Object> damageList(DamageListQuery damageListQuery);
}
