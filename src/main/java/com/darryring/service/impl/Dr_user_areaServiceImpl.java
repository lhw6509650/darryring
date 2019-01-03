package com.darryring.service.impl;

import com.darryring.dao.Dr_user_areaMapper;
import com.darryring.pojo.Dr_user_area;
import com.darryring.service.Dr_user_areaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Dr_user_areaServiceImpl implements Dr_user_areaService {

    @Autowired
    private Dr_user_areaMapper duam;

    //查询所有省份
    public List<Dr_user_area> queryAllSheng(){
        return duam.queryAllSheng();
    }

    //根据省份查询城市
    public List<Dr_user_area> queryShiBySheng(Integer shengId){
        return duam.queryShiBySheng(shengId);
    }

    //根据城市查询区县
    public List<Dr_user_area> queryQuByShi(Integer shiId){
        return duam.queryQuByShi(shiId);
    }

}
