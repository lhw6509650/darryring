package com.darryring.controller;

import com.darryring.service.Dr_product_brandService;
import com.darryring.service.Dr_product_categoryService;
import com.darryring.service.Dr_product_seriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;
@SessionAttributes({"categoryList","ziCategorylist","bdCategorylist"})
@Controller
public class IndexController {

    @Autowired
    private Dr_product_categoryService dcs;

    @Autowired
    private Dr_product_seriesService dpss;

    @Autowired
    private Dr_product_brandService dpbs;


    //显示所有父分类
    @RequestMapping(value = "/index")
    public String index(@RequestParam Map<String,Object> map, Model mo){
        System.out.println("所有父类。。。。");
        mo.addAttribute("categoryList",dcs.selectAll());
        mo.addAttribute("ziCategorylist",dpss.selectAllSeries());
        mo.addAttribute("bdCategorylist",dpbs.selectAllBrand());
        return "index";
    }



}
