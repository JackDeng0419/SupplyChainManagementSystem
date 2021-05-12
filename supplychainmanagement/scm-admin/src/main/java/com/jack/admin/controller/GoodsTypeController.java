package com.jack.admin.controller;


import com.jack.admin.dto.TreeDto;
import com.jack.admin.service.IGoodsTypeService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品类别表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Resource
    private IGoodsTypeService goodsTypeService;

    @RequestMapping("queryAllGoodsTypes")
    @ResponseBody
    public List<TreeDto> queryAllGoodsTypes(Integer typeId){
        // 此处的typeId用于在商品列表页面中筛选，如果是在普通商品页面，不会用到
        return goodsTypeService.queryAllGoodsTypes(typeId);
    }
}
