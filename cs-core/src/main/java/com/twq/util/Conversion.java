package com.twq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * 数据结构转换类
 *
 * @author tianwanqiang
 * @date 2018/1/24 22:50
 */
public class Conversion {
    /**
     * 把json字符串转换为 map
     *
     * @param jsonString
     * @return
     */
    public static Map convertJson2Map(String jsonString) {
        Map<String, String> map = JSON.parseObject(jsonString, new TypeReference<Map<String, String>>() {
        });
        return map;
    }

}
