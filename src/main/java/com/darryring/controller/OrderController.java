package com.darryring.controller;

import com.darryring.pojo.DrUser;
import com.darryring.pojo.Dr_order;
import com.darryring.pojo.Dr_order_detail;
import com.darryring.pojo.Dr_shopcar;
import com.darryring.service.Dr_orderService;
import com.darryring.service.Dr_productService;
import com.darryring.service.Dr_shopcarService;
import com.darryring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class OrderController {

    @Autowired
    Dr_shopcarService dss;
    @Autowired
    Dr_orderService dos;
    @Autowired
    Dr_productService dps;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/order")
    public String order(String typeId,String productId, Model model, HttpServletRequest request){
        System.out.println("进入订单----------");
        DrUser user = (DrUser)request.getSession().getAttribute("user");
        // 创建一个订单对象
        Dr_order order = new Dr_order();
        // 创建一个订单号
        int num = (int) Math.floor(Math.random() * 10000);
        String salNo = "SL" + System.currentTimeMillis() + num;
        // 设置订单信息
        order.setOrderNum(salNo);
        // 设置用户对象
        order.setUid(user.getUserId());
        // 设置创建时间
        order.setCreateTime(new Date());
        // 设置订单状态
        order.setStatus(1);
        // 设置订单详情
        List<Dr_order_detail> orderList = new ArrayList<Dr_order_detail>();
        if ("1".equals(typeId)) {// 通过购物车结算
            // 取出购物车集合
            List<Dr_shopcar> slist = dss.selProById(user);
            // 取出总金额
            Float sumPrice = (Float) request.getSession().getAttribute("sumPrice");
            // 循环购物车
            for (Dr_shopcar car : slist) {
                Dr_order_detail drod = new Dr_order_detail();
                drod.setOid(order.getOid());
                drod.setQuantity(car.getNum());
                drod.setProMap(car.getProMap());
                orderList.add(drod);
            }
            // 设置消费总金额
            order.setSumPrice(sumPrice);
            // 将订单详情集合设置到订单对象
            order.setOrderIitems(orderList);
            int flag = dos.addOrder(order);
            if (flag>0) { // 添加成功
                // 清空购物车数据
                dss.delPro(Integer.parseInt(productId),user.getUserId());
                return "order";
            }
        } else {// 立即结算
            Map<String,Object> proMap =  dps.selProDetail(Integer.parseInt(productId));
            order.setSumPrice((Float) proMap.get("price"));
            Dr_order_detail drod = new Dr_order_detail();
            drod.setOrder(order);
            orderList.add(drod);
            // 将订单详情集合设置到订单对象
            order.setOrderIitems(orderList);
            int flag = dos.addOrder(order);
            if (flag > 0) { // 添加成功
                // 清空购物车数据
                dss.delPro(Integer.parseInt(productId), user.getUserId());
                return "qianduan/order";
            }
        }
        return "qianduan/order";
    }

    @RequestMapping("/pay")
    public String pay(String productId, Model model, HttpServletRequest request){
        System.out.println("进入支付----------");
        return "qianduan/pay";
    }

}
