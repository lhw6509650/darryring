package com.darryring.dao;

import com.darryring.pojo.DrUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrUserDao {
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

    //根据手机号码登录注册
    public int registByPhone(String phone);

    //更改用户个人资料
    public int improve(DrUser du);

    //修改密码
    public int resetPwd(DrUser user);

    //修改密码前判断输入的原密码是否存在
    public List<DrUser> queryByPwd(@Param("password") String password, @Param("userId")Integer userId);




}
