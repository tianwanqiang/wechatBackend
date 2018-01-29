package com.twq.securiy;

import com.twq.dao.model.Users;
import com.twq.service.ConfigService;
import com.twq.service.UsersService;
import com.twq.util.*;
import com.twq.worksflow.msg.ResHeader;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckSign implements Check {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ConfigService managerService;
    private static String CS_APP_KEY;
    private final static Logger LOGGER = CSLog.get();

    public ResHeader check(String rpid, Map<String, String> reqMsg) {
        ResHeader resHeader = new ResHeader();
        String clientSign = reqMsg.get(Nodes.signature);
        Map<String, String> data = new HashMap<String, String>();
        data.putAll(reqMsg);
        data.remove(Nodes.signature);
        String currToken = data.remove(Nodes.token);
        if (!Constants.API_LOGIN_ID.equals(reqMsg.get(Nodes.apiId))) {
            //登录接口不判断用户及token
            String userId = reqMsg.get(Nodes.userId);
            if (userId != null && !userId.isEmpty()) {
                Users user = usersService.getUserById(userId);
                if (user != null) {
                    currToken = user.getSessionId();
                    if (!currToken.equals(reqMsg.get(Nodes.token))) {
                        resHeader.setRetCode(Constants.RET_CODE_ACCESSTOKEN_ERROR, userId);
                        return resHeader;
                    }
                    reqMsg.put(Nodes.accountType, user.getAccountType().toString());
                } else {
                    resHeader.setRetCode(Constants.RET_CODE_LOGINED_USER_NOT_EXIST, userId);
                    return resHeader;
                }
            }
        }
        //签名校验
        if (CS_APP_KEY == null) {
            CS_APP_KEY = managerService.getPlatParameters().get(Nodes.appKey);
        }
        String signString = getSignString(rpid, currToken, data);//生成待签名字符串
        String log_ignore_api = PropertiesUtils.getDynpropsMapperInstans().getProperty(Nodes.log_ignore_api);
        if (!log_ignore_api.contains(reqMsg.get(Nodes.apiId))) {
            CSLog.debugRPID_In(LOGGER, rpid, "【签名校验】sign_string={}", signString);
        }

        String sign = genSign(signString);//生成签名
        if (!clientSign.equals(sign)) {//签名校验不通过
            resHeader.setRetCode(Constants.RET_CODE_SESSION_ERROR, Nodes.signature);
            CSLog.debugRPID_In(LOGGER, rpid, "【签名校验】接口{}签名校验未通过.", reqMsg.get(Nodes.apiId));
        } else {
            CSLog.debugRPID_In(LOGGER, rpid, "【签名校验】接口{}签名校验通过.", reqMsg.get(Nodes.apiId));
        }
        return resHeader;
    }

    private static String getSignString(String rpid, String token, Map<String, String> data) {
        CSLog.debugRPID_In(LOGGER, rpid, "【签名校验】userId={},token={}", data.get(Nodes.userId), token);
        Collection<String> values = data.values();
        String[] signArray = values.toArray(new String[values.size()]);
        String afterBody = token + CS_APP_KEY;
        String SignString;
        if (signArray.length == 1)
            SignString = signArray[0] + afterBody;
        else {
            Arrays.sort(signArray);
            SignString = StringUtil.join(signArray) + afterBody;
        }
        return SignString;
    }

    private static String genSign(String signString) {
        String serverSign = DigestUtils.md5Hex(signString);
        return serverSign;
    }
}
