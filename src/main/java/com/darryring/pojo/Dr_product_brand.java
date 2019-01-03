package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
* 类型表
* */
@Setter
@Getter
public class Dr_product_brand implements Serializable {

    private Integer bid;

    private Integer cid;

    private String bname;
}
