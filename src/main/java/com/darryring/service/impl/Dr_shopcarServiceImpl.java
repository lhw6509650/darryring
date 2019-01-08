package com.darryring.service.impl;

import com.darryring.dao.Dr_shopcarMapper;
import com.darryring.pojo.DrUser;
import com.darryring.pojo.Dr_shopcar;
import com.darryring.service.Dr_shopcarService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Service
public class Dr_shopcarServiceImpl implements Dr_shopcarService {

    @Autowired
    private Dr_shopcarMapper dr_shopcarMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Dr_shopcar> selProById(DrUser user) {
        List<Dr_shopcar> sclist = null;
        if(user==null){
            System.out.println("没有登陆。。。");
            if(redisUtil.exists("sclist")){
                sclist = (List<Dr_shopcar>)redisUtil.get("sclist");
                System.out.println("sclist:"+sclist);
            }else{
                return sclist;
            }
        }else{
            System.out.println("已经登陆了。。。");
            //从数据库取并保存到redis
            sclist = dr_shopcarMapper.selProById(user.getUserId());
            redisUtil.set("sclist", sclist, 24*60L);
        }
        return sclist;
    }

    @Override
    public void addPro(List<Dr_shopcar> sclist,DrUser user) {
        redisUtil.set("sclist", sclist, 7*24*60L);
        if(user!=null){//已登录
            System.out.println("jinru....");
            List<Dr_shopcar> slist = (List<Dr_shopcar>)redisUtil.get("sclist");
            for(Dr_shopcar soc : sclist){
                soc.setUid(user.getUserId());
                dr_shopcarMapper.addPro(soc);
            }
            //插入成功后清空redis缓存
            redisUtil.remove("slist");
        }
    }

    @Override
    public int delPro(Integer pid, Integer uid) {

        return dr_shopcarMapper.delPro(pid,uid);
    }


}
