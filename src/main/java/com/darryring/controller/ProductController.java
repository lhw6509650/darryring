package com.darryring.controller;

import com.darryring.service.Dr_productService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private Dr_productService dps;


    //显示所有父分类
    @RequestMapping(value = "/cdarryring",method = RequestMethod.GET)
    public String findCdr(@RequestParam Map<String,Object> map, Model mo){
        System.out.println("父类下的子类。。。。");
        System.out.println("cid:::::"+map.get("cid"));
        String pageIndex = (String) map.get("pageIndex");
        if (pageIndex == null) {
            pageIndex = "1";
        }
        Page<Object> page = PageHelper.startPage(Integer.parseInt(pageIndex),12);
        dps.queryAllProBy(map);
        mo.addAttribute("page",page);
        mo.addAttribute("map",map);
        System.out.println(page.getPages());
        return "qianduan/cdarryring";
    }

    //显示所有的类型
    @RequestMapping("/cpring")
    public String brand(@RequestParam Map<String,Object> map,Model mo){
        System.out.println("男戒：：："+map.get("pbid"));
        String pageIndex = (String) map.get("pageIndex");
        String cid =  (String)map.get("cid");
        String  bnames = (String) map.get("keyword");
        System.out.println("模糊查系列："+bnames);
        if(pageIndex==null){
            pageIndex="1";
        }
        Page<Object> page = PageHelper.startPage(Integer.parseInt(pageIndex), 6);
        dps.queryAllBrandBy(map);
        mo.addAttribute("page",page);
        System.out.println(".."+page.getResult().size());
        mo.addAttribute("bnames",bnames);
        mo.addAttribute("map",map);
        return "qianduan//cpring";
    }
    //查询根据钻石参数的dpaid和系列的psid查询所有的钻石定制
    //钻戒定制
    @RequestMapping("/customnew")
    public  String  customnew(@RequestParam Map<String,Object> map,Model mo){

        return "qianduan/customnew";
    }



    //商品详情
    @RequestMapping(value = "/proDetail",method = RequestMethod.GET)
    public String proDetail(Integer productId, Model mo){
        System.out.println("商品详情。。。。");
        mo.addAttribute("pdmap",dps.selProDetail(productId));
        mo.addAttribute("pdpiclist",dps.selProPic(productId));
        return "qianduan//goodsJewelry";
    }

}
