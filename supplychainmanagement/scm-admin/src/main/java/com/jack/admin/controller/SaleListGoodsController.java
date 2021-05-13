package com.jack.admin.controller;


import com.jack.admin.query.SaleListGoodsQuery;
import com.jack.admin.service.ISaleListGoodsService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 销售单商品表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/saleListGoods")
public class SaleListGoodsController {
    @Resource
    ISaleListGoodsService saleListGoodsService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> saleListGoodsList(SaleListGoodsQuery saleListGoodsQuery){
        return saleListGoodsService.saleListGoodsList(saleListGoodsQuery);
    }
}
