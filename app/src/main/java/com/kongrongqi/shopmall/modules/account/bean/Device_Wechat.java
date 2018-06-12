package com.kongrongqi.shopmall.modules.account.bean;

import android.databinding.Bindable;

import com.kongrongqi.shopmall.modules.model.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class Device_Wechat extends BaseModel{
    private List<Device> userDeviceList;
    private List<DeviceWechat> userDeviceWechatList;

    @Bindable
    public List<Device> getUserDeviceList() {
        return userDeviceList;
    }

    public void setUserDeviceList(List<Device> userDeviceList) {
        this.userDeviceList = userDeviceList;
    }

    @Bindable
    public List<DeviceWechat> getUserDeviceWechatList() {
        return userDeviceWechatList;
    }

    public void setUserDeviceWechatList(List<DeviceWechat> userDeviceWechatList) {
        this.userDeviceWechatList = userDeviceWechatList;
    }
}



//{"bizCode":0,"code":200,"data":{"userDeviceList":[{"createTime":1495504296000,"dataStatus":0,"deviceName":"","deviceType":"","id":"26767059167396","machineCode":"46548955","mobile":"","owner":"111","pageNumber":1,"pageSize":10,"sn":1,"sort":"","sysId":"","totalRecord":0,"updateTime":1495504296000,"userDeviceName":"1号机","userId":"111"},{"createTime":1495504296000,"dataStatus":0,"deviceName":"","deviceType":"","id":"26808890292630","machineCode":"86219703006047","mobile":"","owner":"111","pageNumber":1,"pageSize":10,"sn":2,"sort":"","sysId":"","totalRecord":0,"updateTime":1495504296000,"userDeviceName":"2号机","userId":"111"}],"userDeviceWechatList":[{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":1,"id":"26767061157120","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":1,"sort":"","state":1,"sysId":"","totalRecord":0,"userDeviceId":"26767059167396","userId":"111","wechatNo":""},{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":0,"id":"26767079820362","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":2,"sort":"","state":1,"sysId":"","totalRecord":0,"userDeviceId":"26767059167396","userId":"111","wechatNo":""},{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":0,"id":"26767087708669","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":3,"sort":"","state":1,"sysId":"","totalRecord":0,"userDeviceId":"26767059167396","userId":"111","wechatNo":""},{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":0,"id":"26767096525211","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":4,"sort":"","state":1,"sysId":"","totalRecord":0,"userDeviceId":"26767059167396","userId":"111","wechatNo":""}]},"msg":""}
//{"bizCode":0,"code":200,"data":{"userDeviceList":[{"createTime":1495504296000,"dataStatus":0,"deviceName":"","deviceType":"","id":"26767059167396","machineCode":"46548955","mobile":"","owner":"111","pageNumber":1,"pageSize":10,"sn":1,"sort":"","sysId":"","totalRecord":0,"updateTime":1495504296000,"userDeviceName":"1号机","userId":"111"},{"createTime":1495504296000,"dataStatus":0,"deviceName":"","deviceType":"","id":"26808890292630","machineCode":"86219703006047","mobile":"","owner":"111","pageNumber":1,"pageSize":10,"sn":2,"sort":"","sysId":"","totalRecord":0,"updateTime":1495504296000,"userDeviceName":"2号机","userId":"111"}],"userDeviceWechatList":[{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":1,"id":"26767061157120","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":1,"sort":"","state":1,"sysId":"","totalRecord":0,"updateTime":null,"userDeviceId":"26767059167396","userId":"111","wechatNo":""},{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":0,"id":"26767079820362","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":2,"sort":"","state":1,"sysId":"","totalRecord":0,"updateTime":null,"userDeviceId":"26767059167396","userId":"111","wechatNo":""},{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":0,"id":"26767087708669","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":3,"sort":"","state":1,"sysId":"","totalRecord":0,"updateTime":null,"userDeviceId":"26767059167396","userId":"111","wechatNo":""},{"createTime":1495527221000,"dataStatus":0,"failureReasons":"","hasGroove":0,"id":"26767096525211","name":"","owner":"111","pageNumber":1,"pageSize":10,"password":"","sn":4,"sort":"","state":1,"sysId":"","totalRecord":0,"updateTime":null,"userDeviceId":"26767059167396","userId":"111","wechatNo":""}]},"msg":""}