package com.jack.admin.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jack.admin.model.RespBean;
import com.jack.admin.pojo.ReturnList;
import com.jack.admin.pojo.ReturnListGoods;
import com.jack.admin.query.ReturnListQuery;
import com.jack.admin.service.IReturnListService;
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
 * 退货单表 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Controller
@RequestMapping("/return")
public class ReturnListController {

    @Resource
    private IReturnListService returnListService;

    @Resource
    private IUserService userService;

    @RequestMapping("index")
    public String index(Model model){
        Model returnNumber = model.addAttribute("returnNumber", returnListService.getNextReturnNumber());
        return "return/return";
    }

//    @RequestMapping("list")
//    @ResponseBody
//    public Map<String,Object> returnList(ReturnListQuery returnListQuery){
//        return returnListService.returnList(returnListQuery);
//    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean save(ReturnList returnList, String goodsJson, Principal principal){
        String userName = principal.getName();
        returnList.setUserId(userService.findUserByUserName(userName).getId());
        Gson gson = new Gson();
        List<ReturnListGoods> rlgList = gson.fromJson(goodsJson,new TypeToken<List<ReturnListGoods>>(){}.getType());
        returnListService.saveReturnList(returnList,rlgList);
        return RespBean.success("商品退货出库成功!");
    }

    /**
     * 退货单据查询页
     * @return
     */
    @RequestMapping("searchPage")
    public String searchPage(){
        return "return/return_search";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> returnList(ReturnListQuery returnListQuery){
        return returnListService.returnList(returnListQuery);
    }

    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id){
        returnListService.deleteReturnList(id);
        return RespBean.success("删除成功");
    }
}
