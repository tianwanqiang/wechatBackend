package com.twq.dao.model;

public class Tags {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.tag_name
     *
     * @mbg.generated
     */
    private String tagName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.head_img
     *
     * @mbg.generated
     */
    private String headImg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.tag_data
     *
     * @mbg.generated
     */
    private String tagData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.user_name
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.id
     *
     * @return the value of tags.id
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.id
     *
     * @param id the value for tags.id
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.tag_name
     *
     * @return the value of tags.tag_name
     * @mbg.generated
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.tag_name
     *
     * @param tagName the value for tags.tag_name
     * @mbg.generated
     */
    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.head_img
     *
     * @return the value of tags.head_img
     * @mbg.generated
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.head_img
     *
     * @param headImg the value for tags.head_img
     * @mbg.generated
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.tag_data
     *
     * @return the value of tags.tag_data
     * @mbg.generated
     */
    public String getTagData() {
        return tagData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.tag_data
     *
     * @param tagData the value for tags.tag_data
     * @mbg.generated
     */
    public void setTagData(String tagData) {
        this.tagData = tagData == null ? null : tagData.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.user_name
     *
     * @return the value of tags.user_name
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.user_name
     *
     * @param userName the value for tags.user_name
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}