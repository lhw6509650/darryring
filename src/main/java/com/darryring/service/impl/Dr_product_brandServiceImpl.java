package com.darryring.service.impl;

import com.darryring.dao.Dr_product_brandMapper;
import com.darryring.pojo.Dr_product_brand;
import com.darryring.service.Dr_product_brandService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class Dr_product_brandServiceImpl implements Dr_product_brandService {

    @Autowired
    private Dr_product_brandMapper dpbm;

    @Autowired
    RedisUtil redisUtil;

    //查询所有的类型
    public List<Dr_product_brand> selectAllBrand(){
        List<Dr_product_brand> blist = null;
        if(redisUtil.exists("blist")){
            System.out.println("exists:4444");
            blist = (List<Dr_product_brand>)redisUtil.get("blist");
            System.out.println("blist:4444");
            for (Dr_product_brand n : blist) {
                System.out.println(n.getBname()+"\t");
            }
        }else{
            System.out.println("not exists:4444");
            //从数据库取并保存到redis
            blist = dpbm.selectAllBrand();
            redisUtil.set("blist", blist, 24*60L);
        }
        return blist;
    }

    @Override
    public List<Dr_product_brand> selectAllBrandByCid(int cid) {
        return dpbm.selectAllBrandByCid(cid);
    }
}
