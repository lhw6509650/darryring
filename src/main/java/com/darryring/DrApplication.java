package com.darryring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.darryring.dao")
@EnableCaching
@SpringBootApplication
public class DrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrApplication.class, args);
    }
}
