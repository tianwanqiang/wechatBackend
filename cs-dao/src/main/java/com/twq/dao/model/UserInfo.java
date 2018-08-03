package com.twq.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.wechat_id
     *
     * @mbg.generated
     */
    private String wechatId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.money
     *
     * @mbg.generated
     */
    private BigDecimal money;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.ava_url
     *
     * @mbg.generated
     */
    private String avaUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.is_join
     *
     * @mbg.generated
     */
    private Integer isJoin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.session_key
     *
     * @mbg.generated
     */
    private String sessionKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.open_id
     *
     * @mbg.generated
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.token
     *
     * @mbg.generated
     */
    private String token;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.created_at
     *
     * @mbg.generated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.nick_name
     *
     * @mbg.generated
     */
    private String nickName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.id
     *
     * @return the value of user_info.id
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.id
     *
     * @param id the value for user_info.id
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.wechat_id
     *
     * @return the value of user_info.wechat_id
     * @mbg.generated
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.wechat_id
     *
     * @param wechatId the value for user_info.wechat_id
     * @mbg.generated
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.money
     *
     * @return the value of user_info.money
     * @mbg.generated
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.money
     *
     * @param money the value for user_info.money
     * @mbg.generated
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.ava_url
     *
     * @return the value of user_info.ava_url
     * @mbg.generated
     */
    public String getAvaUrl() {
        return avaUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.ava_url
     *
     * @param avaUrl the value for user_info.ava_url
     * @mbg.generated
     */
    public void setAvaUrl(String avaUrl) {
        this.avaUrl = avaUrl == null ? null : avaUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.is_join
     *
     * @return the value of user_info.is_join
     * @mbg.generated
     */
    public Integer getIsJoin() {
        return isJoin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.is_join
     *
     * @param isJoin the value for user_info.is_join
     * @mbg.generated
     */
    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.session_key
     *
     * @return the value of user_info.session_key
     * @mbg.generated
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.session_key
     *
     * @param sessionKey the value for user_info.session_key
     * @mbg.generated
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.open_id
     *
     * @return the value of user_info.open_id
     * @mbg.generated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.open_id
     *
     * @param openId the value for user_info.open_id
     * @mbg.generated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.token
     *
     * @return the value of user_info.token
     * @mbg.generated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.token
     *
     * @param token the value for user_info.token
     * @mbg.generated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.created_at
     *
     * @return the value of user_info.created_at
     * @mbg.generated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.created_at
     *
     * @param createdAt the value for user_info.created_at
     * @mbg.generated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.nick_name
     *
     * @return the value of user_info.nick_name
     * @mbg.generated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.nick_name
     *
     * @param nickName the value for user_info.nick_name
     * @mbg.generated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
}