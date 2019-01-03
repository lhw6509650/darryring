package com.darryring.service.impl;

import com.darryring.dao.Dr_collectMapper;
import com.darryring.pojo.Dr_collect;
import com.darryring.service.Dr_collectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Dr_collectServiceImpl implements Dr_collectService {

    @Autowired
    private Dr_collectMapper dr_collectMapper;

    @Override
    public int addCollect(Integer pid, Integer uid) {

        return dr_collectMapper.addCollect(pid,uid);
    }

    @Override
    public Dr_collect selPro(Integer pid, Integer uid) {

        return dr_collectMapper.selPro(pid,uid);
    }
}
