package com.twq.util;

public enum USER_JOIN_STATUS {

    //挑战完成并打卡
    STATUS_JOINED_CHECKED(2),
    //挑战完成未打卡
    STATUS_JOINED_NOTCHECK(1),
    //为挑战
    STATUS_NOT_JOINED(0);

    private int statusValue;

    private USER_JOIN_STATUS(int value) {
        this.statusValue = value;
    }

    public int getStatusValue() {
        return statusValue;
    }
}
