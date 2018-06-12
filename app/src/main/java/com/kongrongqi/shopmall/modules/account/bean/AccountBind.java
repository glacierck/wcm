package com.kongrongqi.shopmall.modules.account.bean;

import android.databinding.Bindable;

import com.kongrongqi.shopmall.modules.model.BaseModel;

/**
 * 设备信息
 */
public class AccountBind extends BaseModel {

    private String bingDeviceState;//未绑定设备
    private Integer dataStatus;
    private String id;
    private String name;
    private String userDeviceId;
    private String userId;//用户设备名称（1号机，2号机....）
    private String wechatNo;//用户ID

    @Bindable
    public String getBingDeviceState() {
        return bingDeviceState;
    }

    public void setBingDeviceState(String bingDeviceState) {
        this.bingDeviceState = bingDeviceState;
    }

    @Bindable
    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
}
