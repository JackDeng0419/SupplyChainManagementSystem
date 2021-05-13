package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.CustomerReturnList;
import com.jack.admin.mapper.CustomerReturnListMapper;
import com.jack.admin.pojo.CustomerReturnListGoods;
import com.jack.admin.pojo.Goods;
import com.jack.admin.query.CustomerReturnListQuery;
import com.jack.admin.service.ICustomerReturnListGoodsService;
import com.jack.admin.service.ICustomerReturnListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.service.IGoodsService;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.DateUtil;
import com.jack.admin.utils.PageResultUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户退货单表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-13
 */
@Service
public class CustomerReturnListServiceImpl extends ServiceImpl<CustomerReturnListMapper, CustomerReturnList> implements ICustomerReturnListService {

    @Resource
    IGoodsService goodsService;

    @Resource
    ICustomerReturnListGoodsService customerReturnListGoodsService;

    @Override
    public String getNextCustomerReturnNumber() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("XT");
            stringBuffer.append(DateUtil.getCurrentDateStr());
            String customerReturnNumber = this.baseMapper.getNextCustomerReturnNumber();
            if(null != customerReturnNumber) {
                stringBuffer.append(StringUtil.formatCode(customerReturnNumber));
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
    public void saveCustomerReturnList(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> crlgList) {
        // 存CRL
        AssertUtil.isTrue(!(this.save(customerReturnList)), "记录添加失败");
        // 获取CRL的ID
        CustomerReturnList temp = this.getOne(new QueryWrapper<CustomerReturnList>().eq("customer_return_number", customerReturnList.getCustomerReturnNumber()));
        // foreach crlg
            // 设置ListID
            // 获得对应Goods
            // goods.inventory + crlg.number
            // goods.status = 2
        // updateGoods
        // save crlg
        crlgList.forEach( crlg-> {
            crlg.setCustomerReturnListId(temp.getId());

            Goods goods = goodsService.getById(crlg.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity() + crlg.getNum());
            goods.setState(2);

            AssertUtil.isTrue(!(goodsService.updateById(goods)), "记录添加失败");
            AssertUtil.isTrue(!(customerReturnListGoodsService.save(crlg)), "记录添加失败");

        });

    }

    @Override
    public Map<String, Object> customerReturnList(CustomerReturnListQuery customerReturnListQuery) {
        IPage<CustomerReturnList> page = new Page<CustomerReturnList>(customerReturnListQuery.getPage(),customerReturnListQuery.getLimit());
        page =  this.baseMapper.customerReturnList(page,customerReturnListQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }
}
