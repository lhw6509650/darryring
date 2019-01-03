package com.darryring.service.impl;

import com.darryring.dao.Dr_product_seriesMapper;
import com.darryring.pojo.Dr_product_series;
import com.darryring.service.Dr_product_seriesService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class Dr_product_seriesServiceImpl implements Dr_product_seriesService {

    @Autowired
    private Dr_product_seriesMapper dpsm;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Dr_product_series> selectAllSeries() {
        List<Dr_product_series> slist = null;
        if(redisUtil.exists("slist")){
            System.out.println("exists:3333");
            slist = (List<Dr_product_series>)redisUtil.get("slist");
            System.out.println("slist:33333");
            for (Dr_product_series n : slist) {
                System.out.println(n.getSname()+"\t");
            }
        }else{
            System.out.println("not exists:3333");
            //从数据库取并保存到redis
            slist = dpsm.selectAllSeries();
            redisUtil.set("slist", slist, 24*60L);
        }
        return slist;
    }
}
