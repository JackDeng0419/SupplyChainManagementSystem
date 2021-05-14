package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.DamageList;
import com.jack.admin.pojo.Goods;
import com.jack.admin.pojo.OverflowList;
import com.jack.admin.mapper.OverflowListMapper;
import com.jack.admin.pojo.OverflowListGoods;
import com.jack.admin.query.OverFlowListQuery;
import com.jack.admin.service.IGoodsService;
import com.jack.admin.service.IOverflowListGoodsService;
import com.jack.admin.service.IOverflowListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * 报溢单表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
@Service
public class OverflowListServiceImpl extends ServiceImpl<OverflowListMapper, OverflowList> implements IOverflowListService {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private IOverflowListGoodsService overflowListGoodsService;

    @Override
    public String getNextOverflowNumber() {
        try {
            StringBuffer stringBuffer =new StringBuffer();
            stringBuffer.append("BS");
            stringBuffer.append(DateUtil.getCurrentDateStr());
            String overflowNumber = this.baseMapper.getNextOverflowNumber();
            if(null !=overflowNumber){
                stringBuffer.append(StringUtil.formatCode(overflowNumber));
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
    public void saveOverflowList(OverflowList overflowList, List<OverflowListGoods> oflgList) {
        AssertUtil.isTrue(!(this.save(overflowList)),"记录添加失败!");
        OverflowList temp = this.getOne(new QueryWrapper<OverflowList>().eq("overflow_number",overflowList.getOverflowNumber()));
        oflgList.forEach(oflg->{
            oflg.setOverflowListId(temp.getId());
            Goods goods = goodsService.getById(oflg.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity()-oflg.getNum());
            goods.setState(2);
            AssertUtil.isTrue(!(goodsService.updateById(goods)),"记录添加失败!");
            AssertUtil.isTrue(!(overflowListGoodsService.save(oflg)),"记录添加失败!");
        });
    }

    @Override
    public Map<String, Object> overFlowList(OverFlowListQuery overFlowListQuery) {
        IPage<OverflowList> page = new Page<OverflowList>(overFlowListQuery.getPage(),overFlowListQuery.getLimit());
        page =  this.baseMapper.overFlowList(page,overFlowListQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());

    }


}
