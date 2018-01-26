package com.twq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.twq.dao.mapper")
public class CsFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(CsFrontApplication.class, args);
    }


}



