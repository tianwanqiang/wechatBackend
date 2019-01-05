package com.twq.util;

import java.util.Map;

public class XMLUtil {


    private static final String PREFIX_XML = "<xml>";

    private static final String SUFFIX_XML = "</xml>";

    private static final String PREFIX_CDATA = "<![CDATA[";

    private static final String SUFFIX_CDATA = "]]>";

    public static String xmlFormat(Map<String, String> parm, boolean isAddCDATA) {

        StringBuffer strbuff = new StringBuffer(PREFIX_XML);
        if (parm != null && parm.size() > 0) {
            for (Map.Entry<String, String> entry : parm.entrySet()) {
                strbuff.append("<").append(entry.getKey()).append(">");
                //这里是用CDATA标签包起来，原本以为body和sign需要包起来，但是发现不需要，又懒得删了，就改成了body1，sign1
                //isNotNullOrEmptyStr是判断不为空的方法
                if ("attach".equalsIgnoreCase(entry.getKey()) || "body1".equalsIgnoreCase(entry.getKey()) || "sign1".equalsIgnoreCase(entry.getKey())) {
                    strbuff.append(PREFIX_CDATA);
                    if (StringUtil.isNotBlank(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                    strbuff.append(SUFFIX_CDATA);
                } else {
                    if (StringUtil.isNotBlank(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                }
                strbuff.append("</").append(entry.getKey()).append(">");
            }
        }
        return strbuff.append(SUFFIX_XML).toString();
    }

}
