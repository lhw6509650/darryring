package com.darryring.controller;

import com.darryring.pojo.DrUser;
import com.darryring.service.Dr_collectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CollectController {

    @Autowired
    private Dr_collectService dcs;

    //显示所有父分类
    @ResponseBody
    @RequestMapping(value = "/collect")
    public String collect(Integer pid, HttpServletRequest request){
        System.out.println("收藏。。。。"+pid);
        String flag = "0";
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        if(user!=null){
            if(dcs.selPro(pid,user.getUserId())==null){
                if(dcs.addCollect(pid,user.getUserId())>0){
                    flag = "1";
                    return flag;
                }
            }else{
                flag = "2";
                return flag;
            }
        }
        return flag;
    }

    @RequestMapping(value = "/user/collect")
    public String userCollect(Integer pid, HttpServletRequest request){
        System.out.println("收藏。。。。"+pid);

        return "";
    }

}
