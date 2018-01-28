package com.twq.util;

/**
 * 业务常量，应该放到数据库中
 */
public class Constants {

    public static final String RET_CODE_SUCCESSFUL = "0";

    //错误码
    public static String RET_CODE_LOGINED_USER_NOT_EXIST = "-999";//用户不存在

    //签名验证未通过
    public static String RET_CODE_AUTO_ERROR = "500";


    //--------系统参数key---------------------
    public static String PARAM_TYPE_SYS = "sys";


    //------数据库常量----------
    public static Integer GENDER_MALE = 1;//男
    public static Integer GENDER_FEMALE = 0;//女


}
