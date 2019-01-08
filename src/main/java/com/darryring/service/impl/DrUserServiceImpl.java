package com.darryring.service.impl;

import com.darryring.dao.DrUserDao;
import com.darryring.pojo.DrUser;
import com.darryring.service.DrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Override
    public int registByPhone(String phone) {

        return drd.registByPhone(phone);
    }

    //更改用户个人资料
    public int improve(DrUser du){
        return drd.improve(du);
    }

    //修改密码
    public int resetPwd(DrUser user){
        return drd.resetPwd(user);
    }

    //修改密码前判断输入的原密码是否存在
    public List<DrUser> queryByPwd(String pwd, Integer userId){
        return drd.queryByPwd(pwd,userId);
    }

    //(后台)根据用户名和密码登录后台
    public DrUser findAllUserByType(String userName,String password){
       return drd.findAllUserByType(userName,password);
    }

    //(后台)根据多条件带分页查询用户
    public List<DrUser> selectAllUserByCon(DrUser user){
        return drd.selectAllUserByCon(user);
    }

    //统计有多少个用户
    public int  selectUser(){
        return drd.selectUser();
    }
    //(后台)新增用户
    public boolean insertUsers(DrUser user){
        return drd.insertUsers(user);
    }
    //(后台)修改用户
    @Override
    public boolean updateUsers(DrUser user) {
        return drd.updateUsers(user);
    }

}
