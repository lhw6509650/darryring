package com.darryring.controller;

import com.darryring.pojo.DrUser;
import com.darryring.pojo.Dr_collect;
import com.darryring.service.Dr_collectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    //我的收藏
    @ResponseBody
    @RequestMapping("myCollect")
    public ModelAndView myColler(Integer catId,ModelAndView mav,HttpServletRequest request){
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        if (catId==null)
            catId = 0;
        List<Map<String,Object>> drlist1 = dcs.queAllCollect(0,user.getUserId());
        List<Map<String,Object>> drlist2 = dcs.queAllCollect(1,user.getUserId());
        List<Map<String,Object>> drlist3 = dcs.queAllCollect(2,user.getUserId());
        mav.addObject("dclist1",drlist1);
        mav.addObject("dclist2",drlist2);
        mav.addObject("dclist3",drlist3);
        mav.setViewName("qianduan/mycollect");
        return mav;
    }

    //移出收藏
    @ResponseBody
    @RequestMapping("removeCollect")
    public ModelAndView removeCollect(Integer colId,ModelAndView mav){
        int removeCollect = dcs.removeCollect(colId);
        if (removeCollect>0)
            System.out.println("该收藏已移出我的收藏！");
        else
            System.out.println("收藏移出失败！");
        mav.setViewName("forward:/myCollect");
        return mav;
    }

    @RequestMapping(value = "/user/collect")
    public String userCollect(Integer pid, HttpServletRequest request){
        System.out.println("收藏。。。。"+pid);

        return "";
    }

}
