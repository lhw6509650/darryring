package com.darryring.dao;


import com.darryring.pojo.Dr_shopcar;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Dr_shopcarMapper {

    public List<Dr_shopcar> selProById(Integer uid);

    public int addPro(Dr_shopcar dr_shopcar);

    public int delPro(@Param("pid") Integer pid,@Param("uid")Integer uid);
}
