package com.jack.admin.query;

import lombok.Data;

@Data
public class SaleListQuery extends BaseQuery{
    private String saleNumber;
    private Integer customerId;
    private Integer state;
    private String startDate;
    private String endDate;
}
