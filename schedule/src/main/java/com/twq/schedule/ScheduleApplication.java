package com.twq.schedule;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduleApplication {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-config.xml");
    }
}
