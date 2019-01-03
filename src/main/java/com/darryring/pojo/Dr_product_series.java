package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
* 系列表
* */
@Setter
@Getter
public class Dr_product_series implements Serializable {

    private Integer sid;

    private Integer cid;

    private String sname;
}
