package com.twq.dao.model;

import java.math.BigDecimal;

public class UserWallet {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_wallet.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_wallet.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_wallet.user_wallet
     *
     * @mbg.generated
     */
    private BigDecimal userWallet;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_wallet.id
     *
     * @return the value of user_wallet.id
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_wallet.id
     *
     * @param id the value for user_wallet.id
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_wallet.user_id
     *
     * @return the value of user_wallet.user_id
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_wallet.user_id
     *
     * @param userId the value for user_wallet.user_id
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_wallet.user_wallet
     *
     * @return the value of user_wallet.user_wallet
     * @mbg.generated
     */
    public BigDecimal getUserWallet() {
        return userWallet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_wallet.user_wallet
     *
     * @param userWallet the value for user_wallet.user_wallet
     * @mbg.generated
     */
    public void setUserWallet(BigDecimal userWallet) {
        this.userWallet = userWallet;
    }
}