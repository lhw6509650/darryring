package com.darryring.service;

import com.darryring.pojo.Dr_user_address;


import java.util.List;

public interface Dr_user_addressService {

    //根据userId查询用户地址
    public List<Dr_user_address> queryAddress(Integer uid);

    //添加用户地址
    public int insertAddress(Integer userId, String address, String receiver, String phone, Integer isDefault);

    //删除用户地址
    public int removeAddress(Integer addressId);

    //根据addressId查询地址
    public Dr_user_address queryAddressByAid(Integer aid);

    //修改用户地址
    public int updateAddress(Dr_user_address address);

}
