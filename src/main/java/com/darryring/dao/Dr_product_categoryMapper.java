package com.darryring.dao;

import com.darryring.pojo.Dr_product_category;

import java.util.List;

public interface Dr_product_categoryMapper {

    //查询所有父分类
    public List<Dr_product_category> selectAll();
}
