package com.twq.service.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.twq.service.base.ConfigService;
import com.twq.service.base.UsersService;
import com.twq.util.CSLog;
import com.twq.util.Nodes;
import com.twq.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class WxService {

    @Autowired
    private ConfigService configService;
    @Autowired
    private UsersService usersService;

    public Object login(String code, String loginUrl) {
        Properties platParameters = configService.getPlatParameters();
        String appKey = (String) platParameters.get(Nodes.APPKEY);
        String appSec = (String) platParameters.get(Nodes.APPSEC);
        String replace = loginUrl.replace("APPID", appKey).replace("SECRET", appSec).replace("JSCODE", code);
        String output = null;
        //TODO 用户信息入库，返回业务令牌
        JSONObject get = WeixinUtil.httpRequest(replace, "GET", output);
        String session_key = (String) get.get("session_key");
        String openId = (String) get.get("openid");
        usersService.registOrUpdate(openId, session_key, null, null);
        CSLog.info("用户信息：" + JSON.toJSONString(get));
        return get;
    }


}
