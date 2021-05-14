package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.DamageList;
import com.jack.admin.mapper.DamageListMapper;
import com.jack.admin.pojo.DamageListGoods;
import com.jack.admin.pojo.Goods;
import com.jack.admin.query.DamageListQuery;
import com.jack.admin.service.IDamageListGoodsService;
import com.jack.admin.service.IDamageListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.service.IGoodsService;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.DateUtil;
import com.jack.admin.utils.PageResultUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报损单表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
@Service
public class DamageListServiceImpl extends ServiceImpl<DamageListMapper, DamageList> implements IDamageListService {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private IDamageListGoodsService damageListGoodsService;

    @Override
    public Object getNextDamageNumber() {
        try {
            StringBuffer stringBuffer =new StringBuffer();
            stringBuffer.append("BS");
            stringBuffer.append(DateUtil.getCurrentDateStr());
            String damageNumber = this.baseMapper.getNextDamageNumber();
            if(null !=damageNumber){
                stringBuffer.append(StringUtil.formatCode(damageNumber));
            }else{
                stringBuffer.append("0001");
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void saveDamageList(DamageList damageList, List<DamageListGoods> plgList) {
        AssertUtil.isTrue(!(this.save(damageList)),"记录添加失败!");
        DamageList temp = this.getOne(new QueryWrapper<DamageList>().eq("damage_number",damageList.getDamageNumber()));
        plgList.forEach(plg->{
            plg.setDamageListId(temp.getId());
            Goods goods = goodsService.getById(plg.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity()-plg.getNum());
            goods.setState(2);
            AssertUtil.isTrue(!(goodsService.updateById(goods)),"记录添加失败!");
            AssertUtil.isTrue(!(damageListGoodsService.save(plg)),"记录添加失败!");
        });
    }

    @Override
    public Map<String, Object> damageList(DamageListQuery damageListQuery) {
        IPage<DamageList> page = new Page<DamageList>(damageListQuery.getPage(),damageListQuery.getLimit());
        page =  this.baseMapper.damageList(page,damageListQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }
}
