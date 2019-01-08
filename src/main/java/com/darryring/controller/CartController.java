package com.darryring.controller;

import com.darryring.pojo.DrUser;
import com.darryring.pojo.Dr_shopcar;
import com.darryring.service.Dr_productService;
import com.darryring.service.Dr_shopcarService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"sumPrice"})
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
    @ResponseBody
    @RequestMapping(value = "/addCart",method = RequestMethod.POST)
    public String addCart(String pdtId, Model model, HttpServletRequest request){
        //是否登录
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        // 根据id返回商品对象
        Map<String,Object> proMap = dps.selProDetail(Integer.parseInt(pdtId));
        // 从数据库中取出购物车集合
        Object obj = dss.selProById(user);
        //总金额
        Float sumPrice = 0.0f;
        // 判断是否是第一次进入购物车
        if (obj == null) {
            // 创建一个购物车对象
            Dr_shopcar scar = new Dr_shopcar();
            // 将商品封装到购物车对象
            scar.setProMap(proMap);
            // 将商品id封装到购物车对象
            scar.setPid((Integer) proMap.get("productId"));
            // 第一次进入，创建购物车集合
            List<Dr_shopcar> sclist = new ArrayList<Dr_shopcar>();
            // 商品数量+1
            scar.setNum(1);
            // 将购物车对象添加到购物车集合中
            sclist.add(scar);
            //将购物车存放至数据库
            dss.addPro(sclist,user);
            System.out.println("第一次进入");
            model.addAttribute("sumPrice", sumPrice);
        } else {
            // 不是第一次进入
            List<Dr_shopcar> sclist = (List<Dr_shopcar>) obj;
            // 准备标记
            boolean flag = false;
            // 循环迭代找到是否有相同商品
            for (Dr_shopcar shopcar : sclist) {
                if (shopcar.getProMap().get("productId").toString().equals(pdtId)) {
                    System.out.println("flag="+flag);
                    // 为相同商品重新设置回去
                    shopcar.setNum(shopcar.getNum() + 1);
                    // 总金额在原来的基础加一个相同的价格
                    sumPrice = sumPrice + (Float) shopcar.getProMap().get("price");
                    dss.upPro(shopcar);
                    model.addAttribute("sumPrice", sumPrice);
                    flag = true;
                    break;
                }
            }
            if (!flag) {// 商品不相同
                // 创建一个购物车对象
                Dr_shopcar scar = new Dr_shopcar();
                List<Dr_shopcar> shclist = new ArrayList<>();
                // 将商品封装到购物车对象
                scar.setProMap(proMap);
                // 将商品id封装到购物车对象
                scar.setPid((Integer)proMap.get("productId"));
                // 设置商品数量为1
                scar.setNum(1);
                // 总金额
                sumPrice = sumPrice + (Float) scar.getProMap().get("price");
                // 把新商品加入购物车集合
                shclist.add(scar);
                System.out.println("商品不相同");
                //将购物车存放至数据库
                dss.addPro(shclist,user);
                model.addAttribute("sumPrice", sumPrice);
            }
        }
        return "true";
    }

     @RequestMapping("/cart")
     public String cart(Model model,HttpServletRequest request){
         //是否登录
         DrUser user = (DrUser)request.getSession().getAttribute("user");
         List<Dr_shopcar> cartlist = dss.selProById(user);
         model.addAttribute("cartlist",cartlist);
        /* if(user!=null){
             dss.addPro(cartlist,user);
             model.addAttribute("cartlist",dss.selProById(user));
         }else {
             model.addAttribute("cartlist",cartlist);
         }*/
        return "qianduan/shopcar";
     }

    @RequestMapping("/add")
    public String add(String productId, Model model,HttpServletRequest request){
       System.out.println("进入加+++++");
        //是否登录
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        List<Dr_shopcar> slist = dss.selProById(user);
        for(Dr_shopcar s : slist){
            if(s.getProMap().get("productId").toString().equals(productId)){
                s.setNum(s.getNum()+1);
                s.setXtotal(s.getXtotal()+(Float) s.getProMap().get("price"));
                dss.upPro(s);
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping("/min")
    public String min(String productId,Model model,HttpServletRequest request){
        System.out.println("进入减----------");
        //是否登录
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        Float sumPrice = (Float)request.getSession().getAttribute("sumPrice");
        List<Dr_shopcar> slist = dss.selProById(user);
        for(Dr_shopcar s : slist){
            if(s.getProMap().get("productId").toString().equals(productId)){
                System.out.println(s.getNum());
                if(s.getNum()>1){
                    s.setNum(s.getNum()-1);
                    s.setXtotal(s.getXtotal()-(Float) s.getProMap().get("price"));
                    sumPrice = sumPrice-(Float) s.getProMap().get("price");
                    dss.upPro(s);
                }else{
                    sumPrice = sumPrice-s.getXtotal();
                    dss.delPro(Integer.parseInt(productId),user.getUserId());
                }
            }
        }
        model.addAttribute("sumPrice",sumPrice);
        return "redirect:/cart";
    }

    @RequestMapping("/del")
    public String del(String productId,Model model,HttpServletRequest request){
        System.out.println("进入删除----------");
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        List<Dr_shopcar> slist = dss.selProById(user);
        Float sumPrice = (Float)request.getSession().getAttribute("sumPrice");
        for(Dr_shopcar s : slist){
            if(s.getProMap().get("productId").toString().equals(productId)){
                sumPrice = sumPrice-s.getXtotal();
                dss.delPro(Integer.parseInt(productId),user.getUserId());
            }
        }
        model.addAttribute("sumPrice",sumPrice);
        return "redirect:/cart";
    }

    @RequestMapping("/delAll")
    public String delAll(Model model,HttpServletRequest request){
        System.out.println("进入清空购物车----------");
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        Float sumPrice = (Float)request.getSession().getAttribute("sumPrice");
        sumPrice = 0f;
        dss.delPro(null,user.getUserId());
        model.addAttribute("sumPrice",sumPrice);
        return "redirect:/cart";
    }

}
