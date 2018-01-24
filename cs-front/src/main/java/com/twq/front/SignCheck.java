package com.twq.front;

import com.twq.dao.model.Users;
import com.twq.util.CSLog;
import com.twq.util.Constants;
import com.twq.util.Nodes;
import com.twq.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口签名校验
 */
@Service
public class SignCheck {


    private static String APPKEY;

    private final Logger logger = CSLog.get(this.getClass());

    public Map check(String rpid, Map<String, String> requestMap) {

        Map<String, String> resHeader = new HashMap<>();
        if (requestMap.containsKey(Nodes.sign)) {
            String userId = requestMap.get(Nodes.userId);
            String clientSign = requestMap.get(Nodes.sign);
            String seesion = "";
            Map<String, String> data = new HashMap<String, String>();
            data.putAll(requestMap);
            data.remove(Nodes.sign);
            if (userId != null) {
                Users user = new Users();
                if (user != null) {
                    seesion = "";//TODO user.getSessionId()
                    requestMap.put(Nodes.accountType, "user.getAccountType()".toString());
                    requestMap.put(Nodes.openId, "user.getOpenId()");//TODO
                } else {
                    resHeader.put(Nodes.retCode, Constants.RET_CODE_LOGINED_USER_NOT_EXIST);
                    return resHeader;
                }
            }

            String signString = getSignString(rpid, seesion, data);//生成客户端签名字符串
//            if (!"8017".equals(requestMap.get(Nodes.apiId))) {
//                CSLog.debugRPID_In(LOGGER, rpid, "【签名校验】signString={}", signString);
//            }

            String sign = genSign(signString);//生成服务端签名
            if (!clientSign.equals(sign)) {//签名校验不通过
                resHeader.put(Nodes.retCode, "");//TODO
                CSLog.debugRPID_In(logger, rpid, "【签名校验】接口{}签名校验未通过.", requestMap.get(Nodes.apiId));
            } else {
                resHeader.put(Nodes.retCode, "");//TODO
                CSLog.debugRPID_In(logger, rpid, "【签名校验】接口{}签名校验通过.", requestMap.get(Nodes.apiId));

            }
        } else {

        }
        return resHeader;
    }

    public String getSignString(String rpid, String session, Map<String, String> data) {
        CSLog.debugRPID_In(logger, rpid, "【签名校验】userId={},session={}", data.get(Nodes.userId), session);
        Collection<String> values = data.values();
        String[] signArray = values.toArray(new String[values.size()]);
        if (APPKEY == null) {
//            APPKEY = configService.getPlatParameters().get(DBConstants.APP_KEY); TODO
        }
        String afterBody = session + APPKEY;
        String SignString;
        if (signArray.length == 1)
            SignString = signArray[0] + afterBody;
        else {
            Arrays.sort(signArray);
            SignString = StringUtil.join(signArray) + afterBody;
        }
        return SignString;
    }

    public String genSign(String SignString) {
        String serverSign = DigestUtils.md5Hex(SignString);
        return serverSign;
    }


}
