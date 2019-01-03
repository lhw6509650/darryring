package com.darryring.service;

import com.darryring.pojo.DrUser;

public interface DrUserService {

    //注册账号
    public int insertUser(DrUser user);

    //根据手机号码和密码登录
    public DrUser findByNamePw(String phone, String password);

    //个人中心修改资料
    public int updateUser(Integer userId);

    //根据用户ID查用户
    public DrUser findAll(Integer userId);

    //根据手机号码判定用户是否存在
    public DrUser findUserByPhone(String phone);
}
