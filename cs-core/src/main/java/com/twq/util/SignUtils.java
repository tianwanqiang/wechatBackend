package com.twq.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Map;

public class SignUtils {
    private final Logger LOGGER = CSLog.get();

    public static void sign(Map<String, String> param) {
        for (Map.Entry<String, String> entry : param.entrySet()) {
            CSLog.info("key={} and value= ", entry.getKey(), entry.getValue());
        }
        String appKey = param.remove("appKey");
        String session = param.remove("session");


        Object paramArray[] = param.values().toArray();
        Arrays.sort(paramArray);
        String signStr = StringUtil.join(paramArray) + session + appKey;
        String sign = DigestUtils.md5Hex(signStr);
        CSLog.info("signStr:{}", signStr);
        param.put("sign", sign);
    }

}
