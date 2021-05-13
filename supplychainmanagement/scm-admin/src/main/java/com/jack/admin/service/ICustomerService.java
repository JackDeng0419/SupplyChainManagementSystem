package com.jack.admin.service;

import com.jack.admin.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.admin.query.CustomerQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
public interface ICustomerService extends IService<Customer> {

    Map<String, Object> customerList(CustomerQuery customerQuery);

    void saveCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Integer[] ids);

    Customer findCustomerByName(String name);

    List<Customer> allCustomers();
}
