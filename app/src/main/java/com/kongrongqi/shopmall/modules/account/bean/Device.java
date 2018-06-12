package com.kongrongqi.shopmall.modules.account.bean;

import android.content.Intent;
import android.databinding.Bindable;

import com.kongrongqi.shopmall.modules.model.BaseModel;

/**
 * 设备信息
 */
public class Device extends BaseModel {

    private Long createTime;
    private Integer dataStatus;
    private String deviceName;//设备型号
    private String deviceType;//设备机器码(IMEI)
    private String id;  //号机的账号id
    private String machineCode;
    private String mobile; //手机号
    private String owner;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer sn;//备序号（1表示 1号机）
    private String sort;
    private Integer state;//0表示正常; 1表示异常

    private String sysId;
    private Integer totalRecord;
    private Long updateTime;
    private String userDeviceName;//用户设备名称（1号机，2号机....）
    private String userId;//用户ID


    @Bindable
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Bindable
    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Bindable
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Bindable
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Bindable
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Bindable
    public Integer getPageNumber() {
        return pageNumber;
    }


    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Bindable
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Bindable
    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    @Bindable
    public String getSort() {
        return sort;
    }


    public void setSort(String sort) {
        this.sort = sort;
    }

    @Bindable
    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    @Bindable
    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    @Bindable
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Bindable
    public String getUserDeviceName() {
        return userDeviceName;
    }


    public void setUserDeviceName(String userDeviceName) {
        this.userDeviceName = userDeviceName;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Device{" +
                "createTime=" + createTime +
                ", dataStatus=" + dataStatus +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", id='" + id + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", owner='" + owner + '\'' +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", sn=" + sn +
                ", sort='" + sort + '\'' +
                ", state=" + state +
                ", sysId='" + sysId + '\'' +
                ", totalRecord=" + totalRecord +
                ", updateTime=" + updateTime +
                ", userDeviceName='" + userDeviceName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
