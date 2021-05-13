package com.jack.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.SaleList;
import com.jack.admin.pojo.SaleListGoods;
import com.jack.admin.pojo.User;
import com.jack.admin.query.SaleListGoodsQuery;
import com.jack.admin.query.SaleListQuery;
import com.jack.admin.service.ISaleListService;
import com.jack.admin.service.IUserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销售单表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-13
 */
@Controller
@RequestMapping("/sale")
public class SaleListController {
    @Resource
    private ISaleListService saleListService;

    @Resource
    private IUserService userService;

    /**
     * 销售出库主页
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("saleNumber",saleListService.getNextSaleNumber());
        return "sale/sale";
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean save(SaleList saleList, String goodsJson, Principal principal){
        saleList.setUserId(userService.findUserByUserName(principal.getName()).getId());
        Gson gson = new Gson();
        List<SaleListGoods> slgList = gson.fromJson(goodsJson, new TypeToken<List<SaleListGoods>>(){}.getType());
        saleListService.saveSaleList(saleList, slgList);
        return RespBean.success("商品销售出库成功");
    }

    @RequestMapping("searchPage")
    public String searchPage(){
        return "sale/sale_search";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> salelist(SaleListQuery saleListQuery){
        return saleListService.saveList(saleListQuery);
    }

}
