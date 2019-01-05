package com.twq.util;

/**
 * 交易类型枚举
 */
public enum FeeTypeEnum {


    FEE_TYPE_CNY {
        @Override
        public String getCode() {
            return "CNY";
        }

        @Override
        public String getName() {
            return "人民币";
        }
    };


    public abstract String getCode();

    public abstract String getName();

}
