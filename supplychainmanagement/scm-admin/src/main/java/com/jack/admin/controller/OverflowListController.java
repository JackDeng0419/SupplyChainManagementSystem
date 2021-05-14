package com.jack.admin.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.OverflowList;
import com.jack.admin.pojo.OverflowListGoods;
import com.jack.admin.query.OverFlowListQuery;
import com.jack.admin.service.IOverflowListService;
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
 * 报溢单表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-14
 */
@Controller
@RequestMapping("/overflow")
public class OverflowListController {
    @Resource
    private IOverflowListService overflowListService;

    @Resource
    private IUserService userService;

    /**
     * 商品报溢主页
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("overflowNumber",overflowListService.getNextOverflowNumber());
        return "overflow/overflow";
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean save(OverflowList overflowList, String goodsJson, Principal principal){
        String userName = principal.getName();
        overflowList.setUserId(userService.findUserByUserName(userName).getId());
        Gson gson = new Gson();
        List<OverflowListGoods> oflgList = gson.fromJson(goodsJson,new TypeToken<List<OverflowListGoods>>(){}.getType());
        overflowListService.saveOverflowList(overflowList,oflgList);
        return RespBean.success("商品报损出库成功!");
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> overFlowList(OverFlowListQuery overFlowListQuery){
        return overflowListService.overFlowList(overFlowListQuery);
    }
}
