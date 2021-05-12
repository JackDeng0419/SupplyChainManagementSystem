package com.jack.admin.controller;


import com.jack.admin.query.SupplierQuery;
import com.jack.admin.service.ISupplierService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 供应商表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    ISupplierService supplierService;

    /**
     * 供应商管理主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "supplier/supplier";
    }

    /**
     * 供应商列表查询接口
     * @param supplierQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> supplierList(SupplierQuery supplierQuery){
        return supplierService.supplierList(supplierQuery);
    }

    /**
     * 添加|更新供应商页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateSupplierPage")
    public String addOrUpdateSupplierPage(Integer id, Model model){
        if(null !=id){
            model.addAttribute("supplier",supplierService.getById(id));
        }
        return "supplier/add_update";
    }




}
