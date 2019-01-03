package com.darryring.service;

import com.darryring.pojo.Dr_product_series;

import java.util.List;

public interface Dr_product_seriesService {

    //根据父类Id查所有的系列
    public List<Dr_product_series> selectAllSeries();

}
