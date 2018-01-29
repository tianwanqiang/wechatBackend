package com.twq.csfront;

import com.alibaba.fastjson.JSON;
import com.twq.util.HttpClientUtil;
import com.twq.util.Nodes;
import com.twq.util.SignUtils;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestApi extends TestCase {

    String localIP = "http://127.0.0.1:8080";//= "http://api.teabest.com" ;
    String testIP = "http://apitest.51findme.com";
    //http://staging.51findme.com/services/transaction/payNotice
    String prdIP = "https://api.51findme.com";
    String testAppKey = "mImPJVmkkAjM1lYOvdInFw++";
    String prdAppKey = "s1nGV1VoRm38+8549QJk";
    String session = "NZc2uEvTD0UtUOB4hGQxibrZo1ZMzPyU";
    String appKey = testAppKey;

    private void initLocalEnv() {
        appKey = testAppKey;
        setUrl(localIP);
    }

    Map<String, String> genParam() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("appKey", appKey);
        param.put("session", session);
        return param;
    }


    private void setUrl(String ip_port) {
        callingApi = ip_port + "/api/calling";
    }

    String callingApi = null;

    private Map<String, String> setTestLocalEnv(String session) {
        appKey = testAppKey;
        this.session = session;
        return genParam();
    }

    public void testCallingApi_8004() {
        initLocalEnv();
        Map<String, String> param = new HashMap<String, String>();
        param.put(Nodes.userId, "800001");
        param.put(Nodes.apiId, "8004");
        String token = "e41dec09d0c34068baedd562ac9b82b9";
        param.put(Nodes.token, token);
        String signature = SignUtils.sign(param);
        System.out.println(signature);
        HttpClientUtil.doPostJson(callingApi, JSON.toJSONString(param));
    }

}
