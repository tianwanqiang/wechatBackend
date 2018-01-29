package com.twq.util;

import java.util.Properties;

/**
 * 业务常量，应该放到数据库中
 */
public class Constants {


    //错误码
    public static final String RET_CODE_SUCCESSFUL = "0";
    public static final String RET_CODE_HEADER_NOT_ENOUGH = "-1";
    public static final String RET_CODE_SESSION_ERROR = "-2";
    public static final String RET_CODE_LOGINED_USER_NOT_EXIST = "-3";
    public static final String RET_CODE_ACCESSTOKEN_ERROR = "-4";
    public static final String RET_CODE_SYN_WANGYI_ERROE = "-5";
    public static final String RET_CODE_CALL_PAY_ERROR = "-6";
    public static final String RET_CODE_ROOM_IS_ONLINE = "-7";
    public static final String RET_CODE_ROOM_TOKEN_ERROR = "-8";
    public static final String RET_CODE_ROOM_NOT_EXIST = "-9";
    public static final String RET_CODE_LOGIN_USER_NOT_EXIST = "-10";
    public static final String RET_CODE_CONFIG_ERROR = "-11";
    public static final String RET_CODE_FIELD_ERROR_ALL = "-12";
    public static final String RET_CODE_FIELD_ERROR_REQ = "-13";
    public static final String RET_CODE_FIELD_ERROR_SEND = "-14";
    public static final String RET_CODE_API_MAPPER_NOT_EXIST = "-15";
    public static final String RET_CODE_FIELD_CONVERT = "-16";
    public static final String RET_CODE_NO_AUTH = "-17";
    public static final String RET_CODE_NOT_ALLOW_GAG_SELF = "-18";
    public static final String RET_CODE_USER_WECHAT_ERRROR = "-19";
    public static final String RET_CODE_UPLOAD_PIC_ERROR = "-20";
    public static final String RET_CODE_UPLOAD_PIC_BIGGER = "-21";
    public static final String RET_CODE_UPLOAD_PIC_FORMAT_ERROR = "-22";
    public static final String RET_CODE_UPLOAD_PIC_BODY_ERROR = "-23";
    public static final String RET_CODE_UPLOAD_PIC_DECODE_ERROR = "-24";
    public static final String RET_CODE_UPLOAD_PIC_LENGTH_ERROR = "-25";
    public static final String GET_RONGIM_TOKEN_FAILE = "-26";
    public static final String RET_CODE_DB_ERROR = "-911";
    public static final String RET_CODE_SYSTEM_ERROR = "-999";
    //--------系统参数key---------------------
    public static String PARAM_TYPE_SYS = "sys";
    //------数据库常量----------
    public static Integer GENDER_MALE = 1;//男
    public static Integer GENDER_FEMALE = 0;//女
    //---配置文件---------------
    public final static Properties apiFieldsProperties = PropertiesUtils.getApiFieldsMapperInstans();
    public final static Properties apiMapperInstans = PropertiesUtils.getApiMapperInstans();
    //-----工作流------
    public static final String works_group_class = "works_group_class";
    public static final String condition_group_class = "condition_group_class";

    public static final String API_LOGIN_ID = "8002";


}
