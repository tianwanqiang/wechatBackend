package com.twq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by twq on 2019-01-05.
 */
public class DateUtil {

    /**
     * 返回格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return String
     */
    public static String getFormatDateNormal(Date date) {
        return getFormatDate2String(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回格式 yyyyMMddHHmmss
     *
     * @param date
     * @return String
     */
    public static String getFormatDateWechatPay(Date date) {
        return getFormatDate2String(date, "yyyyMMddHHmmss");
    }

    private static String getFormatDate2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


}
