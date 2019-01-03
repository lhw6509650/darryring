package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Dr_shopcar {

    private Integer shcaid;
    private Dr_product dr_product;
    private DrUser user;
    private Double xtotal;
    private Integer num;
    private Map<String,Object> proMap;

    public Double getXtotal() {
        return num*(Double)proMap.get("price");
    }

    public void setXtotal(Double xtotal) {
        this.xtotal = xtotal;
    }
}
