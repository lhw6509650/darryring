package com.darryring.service.impl;

import com.darryring.dao.Dr_order_detailMapper;
import com.darryring.pojo.Dr_order_detail;
import com.darryring.service.Dr_order_detailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Dr_order_detailServiceImpl implements Dr_order_detailService {

    @Autowired
    private Dr_order_detailMapper dr_order_detailMapper;

    @Override
    public int addOrderDetail(Dr_order_detail dr_order_detail) {

        return dr_order_detailMapper.addOrderDetail(dr_order_detail);
    }
}
