package com.darryring.dao;

import com.darryring.pojo.Dr_collect;

public interface Dr_collectMapper {

    //添加收藏
    public int addCollect(Integer pid,Integer uid);

    //查询是否存在重复商品
    public Dr_collect selPro(Integer pid, Integer uid);
}
