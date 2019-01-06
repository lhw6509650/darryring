package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Setter
@Getter
public class Dr_shopcar implements Serializable {

    private Integer shcaid;
    private Dr_product dr_product;
    private Integer pid;
    private DrUser user;
    private Integer uid;
    private Float xtotal;
    private Integer num;
    private Map<String,Object> proMap;

    public Float getXtotal() {
        return num*(Float)proMap.get("price");
    }

    public void setXtotal(Float xtotal) {
        this.xtotal = xtotal;
    }
}
