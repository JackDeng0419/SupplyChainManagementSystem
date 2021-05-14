package com.jack.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jack.admin.pojo.DamageList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.admin.query.DamageListQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 报损单表 Mapper 接口
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
public interface DamageListMapper extends BaseMapper<DamageList> {

    String getNextDamageNumber();

    IPage<DamageList> damageList(IPage<DamageList> page, @Param("damageListQuery") DamageListQuery damageListQuery);
}
