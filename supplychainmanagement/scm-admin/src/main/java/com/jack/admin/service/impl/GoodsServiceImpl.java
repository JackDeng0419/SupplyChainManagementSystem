package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.Goods;
import com.jack.admin.mapper.GoodsMapper;
import com.jack.admin.query.GoodsQuery;
import com.jack.admin.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.service.IGoodsTypeService;
import com.jack.admin.service.IGoodsUnitService;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.PageResultUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Resource
    private IGoodsTypeService goodsTypeService;

    @Resource
    private IGoodsUnitService goodsUnitService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveGoods(Goods goods) {
        checkParams(goods.getName(), goods.getTypeId(), goods.getUnit());
        goods.setCode(genGoodsCode());
        goods.setInventoryQuantity(0);
        goods.setState(0);
        goods.setLastPurchasingPrice(0F);
        goods.setIsDel(0);
        AssertUtil.isTrue(!(this.save(goods)),"记录添加失败!");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateGoods(Goods goods) {
        checkParams(goods.getName(), goods.getTypeId(), goods.getUnit());
        AssertUtil.isTrue(!(this.updateById(goods)),"记录更新失败!");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteGoods(Integer id) {
        /**
         * 1.记录必须存在
         * 2.不可删除条件
         *    如果商品已经期初入库 不可删除
         *    商品已发生单据 不能删除
         * 3.执行更新   isDel 0->1
         */
        Goods goods =this.getById(id);
        AssertUtil.isTrue(null == goods,"待删除的商品记录不存在!");
        AssertUtil.isTrue(goods.getState() == 1,"该商品已经期初入库，不能删除!");
        AssertUtil.isTrue(goods.getState() == 2,"该商品已经单据，不能删除!");
        goods.setIsDel(1);
        AssertUtil.isTrue(!(this.updateById(goods)),"商品删除失败!");
    }

    @Override
    public Map<String, Object> goodsList(GoodsQuery goodsQuery) {
        IPage<Goods> page = new Page<Goods>(goodsQuery.getPage(),goodsQuery.getLimit());

        //根据选择的页面id，查询获得其子页面的id
        if(null !=goodsQuery.getTypeId()){
            goodsQuery.setTypeIds(goodsTypeService.queryAllSubTypeIdsByTypeId(goodsQuery.getTypeId()));
        }

        page =  this.baseMapper.queryGoodsByParams(page,goodsQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }

    private void checkParams(String name, Integer typeId, String unit) {
        AssertUtil.isTrue(StringUtil.isEmpty(name),"请指定商品名称!");
        AssertUtil.isTrue(null == typeId,"请指定商品类别!");
        AssertUtil.isTrue(StringUtil.isEmpty(unit),"请指定商品单位!");
    }

    @Override
    public String genGoodsCode() {
        String maxGoodsCode=this.baseMapper.selectOne(new QueryWrapper<Goods>().select("max(code) as code")).getCode();
        if(StringUtil.isNotEmpty(maxGoodsCode)){
            Integer code = Integer.valueOf(maxGoodsCode)+1;
            String codes = code.toString();
            int length = codes.length();
            for (int i = 4; i > length; i--) {
                codes = "0"+codes;
            }
            return codes;
        }else{
            return "0001";
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateStock(Goods goods) {
        /**
         * 商品库存量>0
         * 商品成本价>0
         */
        Goods temp =this.getById(goods.getId());
        AssertUtil.isTrue(null == goods,"待更新的商品记录不存在!");
        AssertUtil.isTrue(goods.getInventoryQuantity()<=0,"库存量必须>0");
        AssertUtil.isTrue(goods.getPurchasingPrice()<=0,"成本价必须>0");
        AssertUtil.isTrue(!(this.updateById(goods)),"商品更新失败!");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteStock(Integer id) {
        Goods temp =this.getById(id);
        AssertUtil.isTrue(null == temp,"待更新的商品记录不存在!");
        AssertUtil.isTrue(temp.getState() == 2,"该商品已经发生单据，不可删除!");
        temp.setInventoryQuantity(0);
        AssertUtil.isTrue(!(this.updateById(temp)),"商品删除失败!");
    }

    @Override
    public Goods getGoodsInfoById(Integer gid) {
        Goods goods = this.getById(gid);
        goods.setUnitName(goodsUnitService.getById(goods.getUnit()).getName());
        goods.setTypeName(goodsTypeService.getById(goods.getTypeId()).getName());
        return goods;
    }
}
