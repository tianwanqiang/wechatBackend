package com.twq.util;

public enum TradeTypeEnum {


//    JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付


    JSAPI("JSPAI", "JSAPI,小程序支付"),
    NATIVE("Native", "Native支付"),
    APP("APP", "APP支付"),
    MWEB("MWEB", "H5支付");


    String code, value;

    TradeTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
