package com.darryring.dao;

import com.darryring.pojo.Dr_product;

import java.util.List;
import java.util.Map;

public interface Dr_productMapper {

    //查询根据父类id所有系列的商品(带条件)
    public List<Map<String,Object>> queryAllProBy(Map<String,Object> map);

    //根据商品ID查询商品详情
    public Map<String,Object> selProDetail(Integer productId);

    //根据商品ID查询商品图片
    public List<Map<String,Object>> selProPic(Integer productId);

    //查询根据父类id所有类型的商品(带条件)
    public List<Map<String,Object>> queryAllBrandBy(Map<String,Object> map);

    //验证(根据父类ID和身份证查找是否存在订单)
    public int selectByOrder(String identityCode);

    //(后台)统计系列下共有多少个商品
    public int  selectSProduct(Dr_product dp);

    //(后台)统计类型下共有多少个商品
    public int  selectBProduct(Dr_product dp);
}
