package com.jack.admin.controller;


import com.jack.admin.query.CustomerReturnListGoodsQuery;
import com.jack.admin.service.ICustomerReturnListGoodsService;
import com.jack.admin.service.ICustomerService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 客户退货单商品表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/customerReturnListGoods")
public class CustomerReturnListGoodsController {

    @Resource
    private ICustomerReturnListGoodsService customerReturnListGoodsService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> customerReturnListGoodsList(CustomerReturnListGoodsQuery customerReturnListGoodsQuery){
        return customerReturnListGoodsService.customerReturnListGoodsList(customerReturnListGoodsQuery);
    }


}
