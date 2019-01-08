package com.darryring.service;

import com.darryring.pojo.DrUser;

import java.util.List;
import java.util.Map;

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

    //根据手机号码登录注册
    public int registByPhone(String phone);

    //更改用户个人资料
    public int improve(DrUser du);

    //修改密码
    public int resetPwd(DrUser user);

    //修改密码前判断输入的原密码是否存在
    public List<DrUser> queryByPwd(String pwd, Integer userId);

    //(后台)根据用户名和密码登录后台
    public DrUser findAllUserByType(String userName,String password);

    //(后台)根据多条件带分页查询用户
    public List<DrUser> selectAllUserByCon(DrUser user);

    //(后台)统计有多少个用户
    public int  selectUser();

    //(后台)新增用户共用
    public boolean insertUsers(DrUser user);

    //(后台)修改用户
    public boolean updateUsers(DrUser user);
}
