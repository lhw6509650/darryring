package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dr_product {
    private Integer productId;
    private double price;
    private String imageRoute;
    private Integer stock;
    private Integer isShow;
    private Integer ringsize;
    private String bracelet;
}