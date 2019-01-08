package com.darryring.service;

import com.darryring.pojo.Dr_collect;

import java.util.List;
import java.util.Map;

public interface Dr_collectService {

    //添加收藏
    public int addCollect(Integer pid,Integer uid);

    //查询是否存在重复商品
    public Dr_collect selPro(Integer pid, Integer uid);

    //查询当前用户的所有收藏
    public List<Map<String,Object>> queAllCollect(Integer cid, Integer uid);

    //移出收藏
    public int removeCollect(Integer colId);

}
