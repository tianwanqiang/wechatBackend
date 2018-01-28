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

    Map<String, String> genParam() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("appKey", appKey);
        param.put("session", session);
        return param;
    }

    private void setUrl(String ip_port, String requestUrl) {
        callingApi = ip_port + requestUrl;
    }

    String callingApi = null;

    private Map<String, String> setTestLocalEnv(String session) {
        appKey = testAppKey;
        this.session = session;
        return genParam();
    }

    public void testCallingApiIndex() {
        Map<String, String> param = setTestLocalEnv("hqV3015hY2hasrjvSCq6RgLNJ6zK5Lt2");//这一步之后，param里就会有 session，appkey
        param.put("userId", "10001");
        param.put(Nodes.apiId, "123");
        SignUtils.sign(param);//这一步之后，param 里就会有sign
        setUrl(localIP, "/index/hello");
        HttpClientUtil.doPostJson(callingApi, JSON.toJSONString(param));
    }
}
