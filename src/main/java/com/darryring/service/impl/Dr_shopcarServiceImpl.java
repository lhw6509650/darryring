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
    public List<Dr_shopcar> selProById(Integer uid) {
        List<Dr_shopcar> sclist = null;
        if(uid==null && redisUtil.exists("sclist")){
            System.out.println("exists:3333....");
            sclist = (List<Dr_shopcar>)redisUtil.get("sclist");
            System.out.println("sclist:33333....");
            for (Dr_shopcar n : sclist) {
                System.out.println(n.getNum()+"\t");
            }
        }else{
            System.out.println("not exists:3333....");
            //从数据库取并保存到redis
            sclist = dr_shopcarMapper.selProById(uid);
            redisUtil.set("sclist", sclist, 24*60L);
        }
        return sclist;
    }

    @Override
    public void addPro(List<Dr_shopcar> sclist,HttpServletRequest request) {
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        redisUtil.set("sclist", sclist, 7*24*60L);
        if(user!=null){//已登录
            System.out.println("jinru....");
            List<Dr_shopcar> slist = (List<Dr_shopcar>)redisUtil.get("sclist");
            for(Dr_shopcar soc : sclist){
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
