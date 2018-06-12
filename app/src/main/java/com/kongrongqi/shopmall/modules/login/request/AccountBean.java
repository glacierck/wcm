package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.modules.model.BaseModel;

/**
 * 创建日期：2017/6/2 0002 on 20:17
 * 作者:penny
 */
public class AccountBean extends BaseModel {

    /**
     * email
     */
    private String email;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String real_name;
    /**
     * 用户ID
     */
    private String accountId;
    /**
     * token
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", username='" + username + '\'' +
                ", real_name='" + real_name + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
