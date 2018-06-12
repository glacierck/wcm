package com.kongrongqi.shopmall.modules.account.bean;

import android.databinding.Bindable;
import android.icu.math.BigDecimal;

import com.kongrongqi.shopmall.modules.model.BaseModel;

/**
 * 设备信息
 */
public class GrooveInfo extends BaseModel {

    private String accountId;

    private String name;

    private String price;

    private int status;

    private String userId;

    private String wechatNo;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
}
