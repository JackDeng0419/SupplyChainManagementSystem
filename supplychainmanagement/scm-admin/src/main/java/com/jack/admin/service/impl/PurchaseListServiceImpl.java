package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.admin.pojo.Goods;
import com.jack.admin.pojo.PurchaseList;
import com.jack.admin.mapper.PurchaseListMapper;
import com.jack.admin.pojo.PurchaseListGoods;
import com.jack.admin.service.IGoodsService;
import com.jack.admin.service.IPurchaseListGoodsService;
import com.jack.admin.service.IPurchaseListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.DateUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 进货单 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Service
public class PurchaseListServiceImpl extends ServiceImpl<PurchaseListMapper, PurchaseList> implements IPurchaseListService {

    @Resource
    IGoodsService goodsService;

    @Resource
    IPurchaseListGoodsService purchaseListGoodsService;

    @Override
    public String getNextPurchaseNumber() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("JH");
            stringBuffer.append(DateUtil.getCurrentDateStr());
            String purchaseNumber = this.baseMapper.getNextPurchaseNumber();
            if(null != purchaseNumber) {
                // 获取后四位编号再+1
                stringBuffer.append(StringUtil.formatCode(purchaseNumber));
            } else {
                stringBuffer.append("0001");
            }
            return stringBuffer.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void savePurchaseList(PurchaseList purchaseList, List<PurchaseListGoods> plgList) {
        // 保存purchaseList
        AssertUtil.isTrue(!(this.save(purchaseList)), "记录添加失败");

        // 在保存plgList之前，需要更新对应商品的库存信息
        PurchaseList temp = this.getOne(new QueryWrapper<PurchaseList>().eq("purchase_number", purchaseList.getPurchaseNumber()));
        plgList.forEach(plg->{
            plg.setPurchaseListId(temp.getId());

            Goods goods = goodsService.getById(plg.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity() + plg.getNum()); // 之前的库存数量+新增数量
            goods.setLastPurchasingPrice(plg.getPrice());
            goods.setState(2); // has inventory
            goodsService.updateById(goods);
        });
        // 保存plgList
        AssertUtil.isTrue(!(purchaseListGoodsService.saveBatch(plgList)), "记录添加失败");
    }
}
