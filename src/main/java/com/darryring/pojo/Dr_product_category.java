package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 父分类表
 * */
@Setter
@Getter
public class Dr_product_category implements Serializable {

    private Integer cid;

    private String typename;

}
