package com.kongrongqi.shopmall.modules.model;

import android.databinding.Bindable;

/**
 * 创建日期：2017/5/24 0024 on 10:14
 * 作者:penny
 */
public class UserModel extends BaseModel {

    /**
     * email : null
     * mobile : 13296606016
     * username : null
     * real_name : 蛇果
     */

    private String email;
    private String mobile;
    private String username;
    private String real_name;
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Bindable
    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", username='" + username + '\'' +
                ", real_name='" + real_name + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
