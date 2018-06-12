package com.kongrongqi.shopmall.modules.model;

import android.databinding.Bindable;

/**
 * 创建日期：2017/5/23 0023 on 20:36
 * 作者:penny
 */
public class LoginModel extends BaseModel {

    /**
     * accountId : 10051
     * token : 5d54e3aaa904765b
     */

    private String accountId;
    private String token;

    @Bindable
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Bindable
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "accountId='" + accountId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
