package com.darryring.service;

import com.darryring.pojo.DrUser;
import com.darryring.pojo.Dr_shopcar;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Dr_shopcarService {

    public List<Dr_shopcar> selProById(DrUser user);

    public void addPro(List<Dr_shopcar> sclist,DrUser user);

    public int delPro(Integer pid,Integer uid);


}
