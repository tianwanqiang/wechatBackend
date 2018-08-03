package com.twq.dao.model;

public class SysConfig {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_config.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_config.config_key
     *
     * @mbg.generated
     */
    private String configKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_config.config_value
     *
     * @mbg.generated
     */
    private String configValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_config.content
     *
     * @mbg.generated
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_config.id
     *
     * @return the value of sys_config.id
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_config.id
     *
     * @param id the value for sys_config.id
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_config.config_key
     *
     * @return the value of sys_config.config_key
     * @mbg.generated
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_config.config_key
     *
     * @param configKey the value for sys_config.config_key
     * @mbg.generated
     */
    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_config.config_value
     *
     * @return the value of sys_config.config_value
     * @mbg.generated
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_config.config_value
     *
     * @param configValue the value for sys_config.config_value
     * @mbg.generated
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_config.content
     *
     * @return the value of sys_config.content
     * @mbg.generated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_config.content
     *
     * @param content the value for sys_config.content
     * @mbg.generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}