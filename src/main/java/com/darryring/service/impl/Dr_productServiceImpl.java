package com.darryring.service.impl;

import com.darryring.dao.Dr_productMapper;
import com.darryring.service.Dr_productService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
@Transactional
public class Dr_productServiceImpl implements Dr_productService {

    @Autowired
    private Dr_productMapper dpm;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Map<String, Object>> queryAllProBy(Map<String, Object> map) {
        /*List<Map<String, Object>> qlist = null;
        if(redisUtil.exists("qlist")){
            System.out.println("exists:");
            qlist = (List<Map<String, Object>>)redisUtil.get("qlist");
            System.out.println("qlist:");
            for (Map<String, Object> n : qlist) {
                System.out.println(n.get("patternName")+"\t");
            }
        }else{
            System.out.println("not exists:");
            //从数据库取并保存到redis
            qlist = dpm.queryAllProBy(map);
            redisUtil.set("qlist", qlist, 24*60L);
        }*/
        return dpm.queryAllProBy(map);
    }

    @Override
    public Map<String, Object> selProDetail(Integer productId) {

        return dpm.selProDetail(productId);
    }

    @Override
    public List<Map<String,Object>> selProPic(Integer productId) {

        return dpm.selProPic(productId);
    }

    @Override
    public List<Map<String, Object>> queryAllBrandBy(Map<String, Object> map) {

        return dpm.queryAllBrandBy(map);
    }

    @Override
    public int selectByOrder(String identityCode) {
        return dpm.selectByOrder(identityCode);
    }
}
