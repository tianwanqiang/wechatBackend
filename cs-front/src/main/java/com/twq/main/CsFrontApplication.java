package com.twq.main;//package com.twq;//package com.twq.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.twq.dao.mapper")
@ComponentScan("com.twq")
public class CsFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(CsFrontApplication.class, args);
    }
}
