package com.kongrongqi.shopmall.modules.model;

import android.databinding.Bindable;

/**
 * 创建日期：2017/5/26 0026 on 19:51
 * 作者:penny
 */
public class BindWechatAccountModel extends BaseModel {

    /**
     * failureReasons : 脚本执行失败516|
     * finishNum : 0
     * hasGroove : 1
     * id : 82249621440893
     * inUseNum : 0
     * machineCode :
     * name : 账号1
     * optType : 1
     * owner : 10094
     * password :
     * sn : 1
     * state : 3
     * userDeviceId : 82249556472058
     * userDeviceName : 1号机
     * userId : 10094
     * wechatNo : qq331936039
     */

    private String failureReasons;
    private int finishNum;
    private int hasGroove;
    private String id;
    private int inUseNum;
    private String machineCode;
    private String name;
    private int optType;
    private String owner;
    private String password;
    private int sn;
    private int state;
    private String userDeviceId;
    private String userDeviceName;
    private String userId;
    private String wechatNo;
    private boolean isCheck;

    private int isSameTypeService;//是否有相同类型的服务 0没有 1 有
    private String sameTypeServiceName;

    public int getIsSameTypeService() {
        return isSameTypeService;
    }

    public void setIsSameTypeService(int isSameTypeService) {
        this.isSameTypeService = isSameTypeService;
    }

    public String getSameTypeServiceName() {
        return sameTypeServiceName;
    }

    public void setSameTypeServiceName(String sameTypeServiceName) {
        this.sameTypeServiceName = sameTypeServiceName;
    }

    @Bindable
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getFailureReasons() {
        return failureReasons;
    }

    public void setFailureReasons(String failureReasons) {
        this.failureReasons = failureReasons;
    }

    public int getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(int finishNum) {
        this.finishNum = finishNum;
    }

    public int getHasGroove() {
        return hasGroove;
    }

    public void setHasGroove(int hasGroove) {
        this.hasGroove = hasGroove;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInUseNum() {
        return inUseNum;
    }

    public void setInUseNum(int inUseNum) {
        this.inUseNum = inUseNum;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOptType() {
        return optType;
    }

    public void setOptType(int optType) {
        this.optType = optType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getUserDeviceName() {
        return userDeviceName;
    }

    public void setUserDeviceName(String userDeviceName) {
        this.userDeviceName = userDeviceName;
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

    @Override
    public String toString() {
        return "BindWechatAccountModel{" +
                "failureReasons='" + failureReasons + '\'' +
                ", finishNum=" + finishNum +
                ", hasGroove=" + hasGroove +
                ", id='" + id + '\'' +
                ", inUseNum=" + inUseNum +
                ", machineCode='" + machineCode + '\'' +
                ", name='" + name + '\'' +
                ", optType=" + optType +
                ", owner='" + owner + '\'' +
                ", password='" + password + '\'' +
                ", sn=" + sn +
                ", state=" + state +
                ", userDeviceId='" + userDeviceId + '\'' +
                ", userDeviceName='" + userDeviceName + '\'' +
                ", userId='" + userId + '\'' +
                ", wechatNo='" + wechatNo + '\'' +
                '}';
    }


}
