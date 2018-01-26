package com.twq.exception;

public class CustomException extends Exception {


    public String getRetCode() {
        return retCode;
    }

    private String retCode;

    public CustomException(String retCode) {
        super(retCode);
        this.retCode = retCode;
    }

    public CustomException(String msg, String retCode) {
        super(msg);
        this.retCode = retCode;
    }



}
