package com.jack.admin.query;


import lombok.Data;

@Data
public class PurchaseListQuery extends BaseQuery{
    private String purchaseNumber;
    private Integer supplierId;
    private Integer state;
    private String startDate;
    private String endDate;
}
