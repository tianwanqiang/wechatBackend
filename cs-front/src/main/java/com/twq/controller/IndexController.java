package com.twq.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.twq.dao.model.Users;
import com.twq.service.UsersService;
import com.twq.util.CSLog;
import com.twq.util.Nodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    UsersService usersService;


    @RequestMapping("/hello")
    public String Hello(HttpServletRequest request) {
        Users users = null;
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> strings = parameterMap.keySet();
        for (String key : strings) {
            CSLog.info("param_key=====>{},param_value=====>{}", key, parameterMap.get(key));
        }
        String userId ="";//= (String) map.get(Nodes.userId);
        try {
            users = usersService.getUserById(userId);
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
