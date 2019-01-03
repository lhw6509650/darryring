package com.darryring.service.impl;

import com.darryring.dao.DrUserDao;
import com.darryring.pojo.DrUser;
import com.darryring.service.DrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DrUserServiceImpl implements DrUserService {
    @Autowired
    private DrUserDao drd;

    //注册账号
    @Override
    public int insertUser(DrUser user) {
        return drd.insertUser(user);
    }

    //根据手机号码和密码登录
    @Override
    public DrUser findByNamePw(String phone, String password) {
        return drd.findByNamePw(phone,password);
    }

    //个人中心修改资料
    @Override
    public int updateUser(Integer userId) {
        return drd.updateUser(userId);
    }

    //根据用户ID查用户
    @Override
    public DrUser findAll(Integer userId) {
        return drd.findAll(userId);
    }

    //根据手机号码判定用户是否存在
    public DrUser findUserByPhone(String phone){ return  drd.findUserByPhone(phone);}
}
