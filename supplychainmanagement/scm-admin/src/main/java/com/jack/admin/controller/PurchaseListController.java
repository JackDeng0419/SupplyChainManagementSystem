package com.jack.admin.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.PurchaseList;
import com.jack.admin.pojo.PurchaseListGoods;
import com.jack.admin.query.PurchaseListQuery;
import com.jack.admin.service.IPurchaseListService;
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
 * 进货单 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseListController {

    @Resource
    IPurchaseListService purchaseListService;

    @Resource
    IUserService userService;

    /**
     * 进货入库主页
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        // 获取进货单号
        String purchaseNumber = purchaseListService.getNextPurchaseNumber();
        model.addAttribute("purchaseNumber",purchaseNumber);
        return "purchase/purchase";
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean save(PurchaseList purchaseList, String goodsJson, Principal principal){
        // principal 中只有userId可以获取
        String userName = principal.getName();
        purchaseList.setUserId(userService.findUserByUserName(userName).getId());

        //转换purchaseListGoods JSON to purchaseListGoods list
        Gson gson = new Gson();
        List<PurchaseListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<PurchaseListGoods>>(){}.getType());
        purchaseListService.savePurchaseList(purchaseList, plgList);

        return RespBean.success("商品进货入库成功");
    }

    /**
     * 进货单查询页
     * @return
     */
    @RequestMapping("searchPage")
    public String searchPage(){
        return "purchase/purchase_search";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> purchaseList(PurchaseListQuery purchaseListQuery){
        return purchaseListService.purchaseList(purchaseListQuery);
    }

}
