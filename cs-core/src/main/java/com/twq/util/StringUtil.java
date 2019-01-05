package com.twq.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class StringUtil extends StringUtils {


    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomChar(int length) {
        char[] chr = {'-', '@', '^', '*', '!', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(36)]);
        }
        return buffer.toString();
    }




}
