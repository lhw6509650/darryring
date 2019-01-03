package com.darryring.service;

import com.darryring.pojo.Dr_product_brand;

import java.util.List;

public interface Dr_product_brandService {

    //查询所有的类型
    public List<Dr_product_brand> selectAllBrand();

    //根据父类Id查所有的子分类之类型
    public List<Dr_product_brand> selectAllBrandByCid(int cid);
}
