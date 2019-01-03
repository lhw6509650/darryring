package com.darryring.service.impl;

import com.darryring.dao.Dr_product_categoryMapper;
import com.darryring.pojo.Dr_product_category;
import com.darryring.service.Dr_product_categoryService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Dr_product_categoryServiceImpl implements Dr_product_categoryService {
    @Autowired
    private Dr_product_categoryMapper dcm;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Dr_product_category> selectAll() {
        List<Dr_product_category> clist = null;
        if(redisUtil.exists("clist")){
            System.out.println("exists:2222");
            clist = (List<Dr_product_category>)redisUtil.get("clist");
            System.out.println("clist:2222");
            for (Dr_product_category n : clist) {
                System.out.println(n.getTypename()+"\t");
            }
        }else{
            System.out.println("not exists:2222");
            //从数据库取并保存到redis
            clist = dcm.selectAll();
            redisUtil.set("clist", clist, 24*60L);
        }
        return clist;
    }
}
