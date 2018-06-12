package com.kongrongqi.shopmall.modules.account.bean;

import com.kongrongqi.shopmall.modules.model.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class ServiceDatails  extends BaseModel {
    private List<UserService> userServiceList;
    private DeviceWechat userWechat;


    public List<UserService> getUserServiceList() {
        return userServiceList;
    }

    public void setUserServiceList(List<UserService> userServiceList) {
        this.userServiceList = userServiceList;
    }

    public DeviceWechat getUserWechat() {
        return userWechat;
    }

    public void setUserWechat(DeviceWechat userWechat) {
        this.userWechat = userWechat;
    }
}
