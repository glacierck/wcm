package com.kongrongqi.shopmall.modules.model;

import com.kongrongqi.shopmall.base.BaseBean;

import static com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper.TASK;

/**
 * 输入参数：type 服务类型 1 账号号槽 2 邀友 3 精准邀友 4 入群服务 5账号托管 11 新服务套餐1 12 新服务套餐2，
 * wechatNo 微信号，
 * groupName 群名称（入群服务），
 * callTxt 招呼语（灌粉服务B）
 * deviceWechatId 设备账号表id,
 * userId 用户id
 */
public class UserServiceParam  extends BaseModel{
    private String userId;
    private int type;//服务类型
    private String groupName;//入群名称
    private String deviceWechatId;//号槽id
    private String wechatNo;//微信号
    private String callTxt;//打招呼
    private String id;//
    private String serviceName;
    private int entrance;//入口 设备 ACCOUNT = 0x001;设备   进程 TASK = 0x002;
    private int callType;//招呼语类型

    public UserServiceParam() {
        this.userId = "";
        this.type = 0;
        this.deviceWechatId = "";
        this.groupName = "";
        this.wechatNo = "";
        this.callTxt = "";
        this.id="";
        this.entrance=TASK;
        this.serviceName="";
        this.callType=0;
    }


    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeviceWechatId() {
        return deviceWechatId;
    }

    public void setDeviceWechatId(String deviceWechatId) {
        this.deviceWechatId = deviceWechatId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getCallTxt() {
        return callTxt;
    }

    public void setCallTxt(String callTxt) {
        this.callTxt = callTxt;
    }
}
