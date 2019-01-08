package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class Dr_order {
    private Integer oid;
    private Integer uid;
    private DrUser user;
    private Integer aid;
    private Dr_user_address address;
    private String orderNum;
    private Date createTime;
    private Float sumPrice;
    private Integer status;
    // 一个订单下面有多条订单详情
    private List<Dr_order_detail> orderIitems;
}
