package com.twq.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.twq.dao.mapper.UsersMapper;
import com.twq.dao.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    UsersMapper usersMapper;

    @RequestMapping("/hello")
    public String Hello() {
        Users users = null;
        try {
            users = usersMapper.selectByPrimaryKey("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (users != null) {
            return JSONUtils.toJSONString(users);
        } else {
            return "这里没有查到用户!";
        }

    }
}
