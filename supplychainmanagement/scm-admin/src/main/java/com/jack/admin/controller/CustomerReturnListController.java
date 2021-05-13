package com.jack.admin.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.CustomerReturnList;
import com.jack.admin.pojo.CustomerReturnListGoods;
import com.jack.admin.pojo.Goods;
import com.jack.admin.query.CustomerReturnListQuery;
import com.jack.admin.service.ICustomerReturnListService;
import com.jack.admin.service.IUserService;
import com.jack.admin.service.impl.CustomerReturnListServiceImpl;
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
 * 客户退货单表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-13
 */
@Controller
@RequestMapping("/customerReturn")
public class CustomerReturnListController {

    @Resource
    ICustomerReturnListService customerReturnListService;

    @Resource
    IUserService userService;

    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("customerReturnNumber", customerReturnListService.getNextCustomerReturnNumber());
        return "customerReturn/customer_return";
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean save(CustomerReturnList customerReturnList, String goodsJson, Principal principal){
        customerReturnList.setUserId(userService.findUserByUserName(principal.getName()).getId());
        Gson gson = new Gson();
        List<CustomerReturnListGoods> crlgList = gson.fromJson(goodsJson, new TypeToken<CustomerReturnListGoods>(){}.getType());
        customerReturnListService.saveCustomerReturnList(customerReturnList, crlgList);
        return RespBean.success("商品退货入库成功");
    }

    /**
     * 退货单查询页
     * @return
     */
    @RequestMapping("searchPage")
    public String searchPage(){
        return "customerReturn/customer_return_search";
    }


    /**
     * 退货单列表
     * @param customerReturnListQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> customerReturnList(CustomerReturnListQuery customerReturnListQuery){
        return customerReturnListService.customerReturnList(customerReturnListQuery);
    }
}
