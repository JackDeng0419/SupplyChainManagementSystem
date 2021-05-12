package com.jack.admin.controller;

import com.jack.admin.model.GoodsModel;
import com.jack.admin.pojo.Goods;
import com.jack.admin.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("common")
public class CommonController {

    @Resource
    IGoodsService goodsService;

    /**
     * 添加商品-选择商品页
     * @return
     */
    @RequestMapping("toSelectGoodsPage")
    public String toSelectGoodsPage(){
        return "common/goods";
    }

    @RequestMapping("toAddGoodsInfoPage")
    public String toAddGoodsInfoPage(Integer gid, Model model){
        model.addAttribute("goods", goodsService.getGoodsInfoById(gid));
        return "common/goods_add_update";
    }

    @RequestMapping("toUpdateGoodsInfoPage")
    public String toUpdateGoodsInfoPage(GoodsModel goodsModel, Model model){
        Goods goods = goodsService.getGoodsInfoById(goodsModel.getId());
        goodsModel.setCode(goods.getCode());
        goodsModel.setModel(goods.getModel());
        goodsModel.setName(goods.getName());
        goodsModel.setUnit(goods.getUnitName());
        goodsModel.setTypeId(goods.getTypeId());
        goodsModel.setTypeName(goods.getTypeName());
        goodsModel.setLastPurchasingPrice(goods.getLastPurchasingPrice());
        goodsModel.setInventoryQuantity(goods.getInventoryQuantity());
        model.addAttribute("goods",goodsModel);
        model.addAttribute("flag",1);
        return "common/goods_add_update";
    }
}
