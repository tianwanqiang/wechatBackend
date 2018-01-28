package com.twq.message;

import com.twq.dao.model.Users;
import com.twq.util.CSLog;
import com.twq.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.Arrays;

public abstract class ReqMsg implements Serializable {
    private final Logger LOGGER = CSLog.get();
    public String rpid;
    public String apiId;
    public String password;
    public String accountType;
    public String userId;
    public String total;
    public String sex;
    public String birthday;
    public String location;
    public String toUserId;
    public String sign;
    public Users extra;

    public Users getExtra() {
        return extra;
    }

    public void setExtra(Users extra) {
        this.extra = extra;
    }

    public Logger getLOGGER() {
        return LOGGER;
    }

    public String getRpid() {
        return rpid;
    }

    public String getApiId() {
        return apiId;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getUserId() {
        return userId;
    }

    public String getTotal() {
        return total;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return location;
    }

    public String getToUserId() {
        return toUserId;
    }

    public String getSign() {
        return sign;
    }

    public String getSessionid() {
        return sessionid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getRongyunToken() {
        return rongyunToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setRongyunToken(String rongyunToken) {
        this.rongyunToken = rongyunToken;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String sessionid;
    public String mobileNumber;
    public String rongyunToken;
    public String openId;

    public String getSignString(String methodName, String appKey, String sessionId) {
        CSLog.debugRPID(LOGGER, rpid, "SessionId: {}", sessionId);
        String paramArray[] = sortFiled(methodName);
        CSLog.debugRPID(LOGGER, rpid, "sortFiled(methodName) - {}", paramArray);

        if (paramArray == null) {
            paramArray = sortFiled();
            CSLog.debugRPID(LOGGER, rpid, "sortFiled - {}", paramArray);
        }

        for (int i = 0; i < paramArray.length; i++) {
            CSLog.debugRPID(LOGGER, rpid, "待验证参数{}: {}", i, paramArray[i]);
        }

        CSLog.debugRPID(LOGGER, rpid, "待验证参数个数: {}", paramArray.length);
        if (paramArray.length > 1) {
            CSLog.debugRPID(LOGGER, rpid, "待验证参数: {} - {}", paramArray[0], paramArray[1]);
            Arrays.sort(paramArray);
        }
        String signStr = StringUtil.join(paramArray) + sessionId + appKey;
        return signStr;
    }

    public boolean checkSession(String methodName, Users user, String appKey) {
        String serverSignStr = getSignString(methodName, appKey, user.getSessionId());
        CSLog.debugRPID(LOGGER, rpid, "Server signStr: {}", serverSignStr);
        String serverSign = DigestUtils.md5Hex(serverSignStr);
        CSLog.debugRPID(LOGGER, rpid, "Server    sign: {}", serverSign);
        CSLog.debugRPID(LOGGER, rpid, "Client    sign: {}", sign);
        if (!this.sign.equals(serverSign)) {
            return false;
        } else {
//            this.setSessionid(user.getSessionId());
//            this.setRongyunToken(user.getRongyunToken());
//            if (mobileNumber == null && user.getMobile() != null)
//                this.setMobileNumber(user.getMobile());
//            this.setExtra(user);
//            return true; TODO
        }
        return true;
    }

    public abstract String[] sortFiled();

    public String[] sortFiled(String method) {
        return null;
    }
}
