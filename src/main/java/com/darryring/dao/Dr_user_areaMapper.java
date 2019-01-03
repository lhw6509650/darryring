package com.darryring.dao;

import com.darryring.pojo.Dr_user_area;


import java.util.List;

public interface Dr_user_areaMapper {

    //查询所有省份
    public List<Dr_user_area> queryAllSheng();

    //根据省份查询城市
    public List<Dr_user_area> queryShiBySheng(Integer shengId);

    //根据城市查询区县
    public List<Dr_user_area> queryQuByShi(Integer shiId);
}
