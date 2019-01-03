package com.darryring.service;

import java.util.List;
import java.util.Map;

public interface Dr_productService {

    //查询根据父类id所有的商品(带条件)
    public List<Map<String,Object>> queryAllProBy(Map<String,Object> map);

    //根据商品ID查询商品详情
    public Map<String,Object> selProDetail(Integer productId);

    //根据商品ID查询商品图片
    public List<Map<String,Object>> selProPic(Integer productId);

    //查询根据父类id所有类型的商品(带条件)
    public List<Map<String,Object>> queryAllBrandBy(Map<String,Object> map);
}
