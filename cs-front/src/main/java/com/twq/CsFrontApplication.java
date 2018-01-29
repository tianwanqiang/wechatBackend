package com.twq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.twq.dao.mapper")
@ImportResource(locations={"classpath:config_api/common_unit.xml"})
public class CsFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(CsFrontApplication.class, args);
    }


}



