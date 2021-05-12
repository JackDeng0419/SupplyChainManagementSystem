package com.jack.admin.controller;


import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.Customer;
import com.jack.admin.query.CustomerQuery;
import com.jack.admin.service.ICustomerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private ICustomerService customerService;

    /**
     * 客户管理主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "customer/customer";
    }

    @RequestMapping("addOrUpdateCustomerPage")
    public String addRolePage(Integer id, Model model){
        if(null !=id){
            model.addAttribute("customer",customerService.getById(id));
        }
        return "customer/add_update";
    }

    /**
     * 客户列表查询
     * @param customerQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> customerList(CustomerQuery customerQuery){
        return customerService.customerList(customerQuery);
    }

    /**
     * 保存客户
     * @param Customer
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveCustomer(Customer Customer){
        customerService.saveCustomer(Customer);
        return RespBean.success("记录添加成功");
    }

    /**
     * 更新客户
     * @param Customer
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateCustomer(Customer Customer){
        customerService.updateCustomer(Customer);
        return RespBean.success("记录更新成功");
    }

    /**
     * 删除客户
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteCustomer(Integer[] ids){
        customerService.deleteCustomer(ids);
        return RespBean.success("客户记录删除成功");
    }

}
