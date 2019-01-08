package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Dr_order_detail {
    private Integer odid;
    private Integer oid;
    private Dr_order order;
    private Map<String,Object> proMap;
    private Integer quantity;
}
