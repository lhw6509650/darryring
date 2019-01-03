package com.darryring.dao;

import com.darryring.pojo.Dr_user_address;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Dr_user_addressMapper {

    //根据userId查询用户地址
    public List<Dr_user_address> queryAddress(Integer userId);

    //添加用户地址
    public int insertAddress(@Param("userId") Integer userId,
                             @Param("address") String address,
                             @Param("receiver") String receiver,
                             @Param("phone") String phone,
                             @Param("isDefault") Integer isDefault);

    //删除用户地址
    public int removeAddress(@Param("addressId") Integer addressId);

    //根据addressId查询地址
    public Dr_user_address queryAddressByAid(@Param("aid") int aid);

    //修改用户地址
    public int updateAddress(Dr_user_address address);
}
