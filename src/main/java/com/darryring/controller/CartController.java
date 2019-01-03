package com.darryring.controller;

import com.darryring.pojo.Dr_shopcar;
import com.darryring.service.Dr_productService;
import com.darryring.service.Dr_shopcarService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    Dr_shopcarService dss;
    @Autowired
    Dr_productService dps;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 未登录时
     */
    @RequestMapping(value = "/addCart/{pdtId}",method = RequestMethod.GET)
    public String addCart(@PathVariable String pdtId, Model model, HttpServletRequest request){
        // 根据id返回商品对象
        Map<String,Object> proMap = dps.selProDetail(Integer.parseInt(pdtId));
        // 从商品中取出购物车集合
        Object obj = dss.selProById(null);
        // 创建一个购物车对象
        Dr_shopcar scar = new Dr_shopcar();
        //总金额
        Double sumPrice = 0.0;
        // 将商品封装到购物车对象
        scar.setProMap(proMap);
        // 判断是否是第一次进入购物车
        if (obj == null) {
            // 第一次进入，创建购物车集合
            List<Dr_shopcar> sclist = new ArrayList<Dr_shopcar>();
            // 商品数量+1
            scar.setNum(1);
            // 将购物车对象添加到购物车集合中
            sclist.add(scar);
            //将购物车存放至数据库
            dss.addPro(sclist,request);
            model.addAttribute("shops", sclist);
            model.addAttribute("sumPrice", sumPrice);
        } else {
            // 不是第一次进入
            List<Dr_shopcar> sclist = (List<Dr_shopcar>) obj;
            // 准备标记
            boolean flag = false;
            // 循环迭代找到是否有相同商品
            for (Dr_shopcar shopcar : sclist) {
                if (shopcar.getProMap().get("productId").equals(pdtId)) {
                    // 为相同商品重新设置回去
                    shopcar.setNum(shopcar.getNum() + 1);
                    // 总金额在原来的基础加一个相同的价格
                    sumPrice = sumPrice + (Double) shopcar.getProMap().get("price");
                    model.addAttribute("sumPrice", sumPrice);
                    flag = true;
                    break;
                }
            }
            if (!flag) {// 商品不相同
                // 设置商品数量为1
                scar.setNum(1);
                // 总金额
                sumPrice = sumPrice + (Double) scar.getProMap().get("price");
                // 把新商品加入购物车集合
                sclist.add(scar);
                model.addAttribute("shops", sclist);
                model.addAttribute("sumPrice", sumPrice);
            }
        }
        return "forward:/shopcars";
    }

    @RequestMapping("/cart")
    public String cart(){

        return "index";
    }
}
