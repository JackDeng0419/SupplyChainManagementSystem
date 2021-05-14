package com.jack.admin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 乐字节  踏实教育 用心服务
 *
 * @author 乐字节--老李
 * @version 1.0
 */
@Data
public class SaleCount {
    @ApiModelProperty(value = "成本总金额")
    private float amountCost; //

    @ApiModelProperty(value = "销售总金额")
    private float amountSale; // 销售总金额

    private float amountProfit; // 销售利润

    @ApiModelProperty(value = "日期")
    private String date; // 日期
}
