package com.kongrongqi.shopmall.modules.login.request;

import android.databinding.Bindable;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/5/25 0025 on 14:36
 * 作者:penny
 */
public class RequestUser extends BaseRequest {

    /**
     * nickName : string
     * userId : string
     */

    private String nickName;
    private String mobile;
    public int status;
    private int deviceType;//设备类型

    public int getStatus() {
        return status;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Bindable
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @Override
    public String toString() {
        return "RequestUser{" +
                "nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                '}';
    }
}
