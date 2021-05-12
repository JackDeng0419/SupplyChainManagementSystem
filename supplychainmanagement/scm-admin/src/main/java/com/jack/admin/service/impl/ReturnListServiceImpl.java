package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.admin.pojo.Goods;
import com.jack.admin.pojo.ReturnList;
import com.jack.admin.mapper.ReturnListMapper;
import com.jack.admin.pojo.ReturnListGoods;
import com.jack.admin.query.ReturnListQuery;
import com.jack.admin.service.IGoodsService;
import com.jack.admin.service.IReturnListGoodsService;
import com.jack.admin.service.IReturnListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.admin.utils.AssertUtil;
import com.jack.admin.utils.DateUtil;
import com.jack.admin.utils.PageResultUtil;
import com.jack.admin.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 退货单表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Service
public class ReturnListServiceImpl extends ServiceImpl<ReturnListMapper, ReturnList> implements IReturnListService {

    @Resource
    private IReturnListGoodsService returnListGoodsService;

    @Resource
    private IGoodsService goodsService;

    @Override
    public String getNextReturnNumber() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("TH");
            stringBuffer.append(DateUtil.getCurrentDateStr());
            String returnNumber = this.baseMapper.getNextPurchaseNumber();
            if(null != returnNumber) {
                // 获取后四位编号再+1
                stringBuffer.append(StringUtil.formatCode(returnNumber));
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
    public void saveReturnList(ReturnList returnList, List<ReturnListGoods> rlgList) {
        AssertUtil.isTrue(!(this.save(returnList)),"记录添加失败!");
        ReturnList  temp = this.getOne(new QueryWrapper<ReturnList>().eq("return_number",returnList.getReturnNumber()));
        rlgList.forEach(rlg->{
            rlg.setReturnListId(temp.getId());

            Goods goods =goodsService.getById(rlg.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity()-rlg.getNum());
            goods.setState(2);
            goodsService.updateById(goods);

        });
        AssertUtil.isTrue(!(returnListGoodsService.saveBatch(rlgList)),"记录添加失败!");
    }

    @Override
    public Map<String, Object> returnList(ReturnListQuery returnListQuery) {
        IPage<ReturnList> page = new Page<ReturnList>(returnListQuery.getPage(),returnListQuery.getLimit());
        page =  this.baseMapper.returnList(page,returnListQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }

    @Override
    public void deleteReturnList(Integer id) {
        /**
         * 1.退货单商品记录删除
         * 2.退货单记录删除
         */
        AssertUtil.isTrue(!(returnListGoodsService.remove(new QueryWrapper<ReturnListGoods>().eq("return_list_id",id))),
                "记录删除失败!");
        AssertUtil.isTrue(!(this.removeById(id)),"记录删除失败!");
    }


}
