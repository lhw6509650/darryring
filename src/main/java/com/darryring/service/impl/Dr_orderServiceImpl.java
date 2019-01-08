package com.darryring.service.impl;

import com.darryring.dao.Dr_orderMapper;
import com.darryring.dao.Dr_order_detailMapper;
import com.darryring.pojo.Dr_order;
import com.darryring.pojo.Dr_order_detail;
import com.darryring.service.Dr_orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Dr_orderServiceImpl implements Dr_orderService {

    @Autowired
    private Dr_orderMapper dr_orderMapper;
    @Autowired
    private Dr_order_detailMapper dodm;

    @Override
    public Boolean addOrder(Dr_order order) {
        int num1 = 0;
        int num2 = 0;
        // 循环插入订单详情
        for (Dr_order_detail eod : order.getOrderIitems()) {
            num1 = 11 * dodm.addOrderDetail(eod);
        }
        // 插入订单
        if (num1 != 0) {
            num2 = dr_orderMapper.addOrder(order);
        }
        return num2 > 0 ? true : false;
    }
}
