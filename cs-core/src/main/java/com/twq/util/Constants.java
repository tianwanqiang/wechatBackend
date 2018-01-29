package com.twq.util;

/**
 * 业务常量，应该放到数据库中
 */
public class Constants {



    //错误码
    public static final String RET_CODE_SUCCESSFUL = "0";
    public static final String RET_CODE_LOGINED_USER_NOT_EXIST = "-1";//用户不存在
    public static final String RET_CODE_AUTO_ERROR = "-2"; //签名验证未通过
    public static final String RET_CODE_SYSTEM_ERROR = "-999";//系统错误





    //--------系统参数key---------------------
    public static String PARAM_TYPE_SYS = "sys";


    //------数据库常量----------
    public static Integer GENDER_MALE = 1;//男
    public static Integer GENDER_FEMALE = 0;//女


}
