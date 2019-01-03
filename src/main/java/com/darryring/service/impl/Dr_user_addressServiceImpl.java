package com.darryring.service.impl;

import com.darryring.dao.Dr_user_addressMapper;
import com.darryring.pojo.Dr_user_address;
import com.darryring.service.Dr_user_addressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Dr_user_addressServiceImpl implements Dr_user_addressService {

    @Autowired
    private Dr_user_addressMapper duam;

    //根据userId查询用户地址
    public List<Dr_user_address> queryAddress(Integer uid){
        return duam.queryAddress(uid);
    }

    //添加用户地址
    public int insertAddress(Integer userId,String address,String receiver,String phone,Integer isDefault){
        return duam.insertAddress(userId,address,receiver,phone,isDefault);
    }

    //删除用户地址
    public int removeAddress(Integer addressId){
        return duam.removeAddress(addressId);
    }

    //根据addressId查询地址
    public Dr_user_address queryAddressByAid(Integer aid){
        return duam.queryAddressByAid(aid);
    }

    //修改用户地址
    public int updateAddress(Dr_user_address address){
        return duam.updateAddress(address);
    }

}
