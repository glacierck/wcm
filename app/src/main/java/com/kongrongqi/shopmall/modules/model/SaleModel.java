package com.kongrongqi.shopmall.modules.model;

import com.kongrongqi.shopmall.http.BaseResponse;

/**
 * Created on 2017/7/24 0024.
 * by penny
 */

public class SaleModel extends BaseResponse{

    /**
     * id : P00000000002133422500
     * dataStatus : 0
     * isPublish : 1
     * sn : 4
     * serviceDuration : 360
     * serviceType : 2
     * serviceTypeName : 加友服务A
     * attr1 : null
     * owner : null
     * priceUnit : 元
     * serviceDurationUnit : 天
     * serviceExplain : 年缴6.6折优惠
     * servicePrice : 950
     * serviceHisPrice : 1440
     * sysId : null
     * createTime : 1500915268000
     * updateTime : null
     */
    private boolean isCheck;
    private int isHot;
    private String id;
    private int dataStatus;
    private int isPublish;
    private int sn;
    private int serviceDuration;
    private int serviceType;
    private String serviceTypeName;
    private Object attr1;
    private Object owner;
    private String priceUnit;
    private String serviceDurationUnit;
    private String serviceExplain;
    private String servicePrice;
    private String serviceHisPrice;
    private Object sysId;
    private long createTime;
    private Object updateTime;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean pCheck) {
        isCheck = pCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public int getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(int isPublish) {
        this.isPublish = isPublish;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public int getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(int serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public Object getAttr1() {
        return attr1;
    }

    public void setAttr1(Object attr1) {
        this.attr1 = attr1;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getServiceDurationUnit() {
        return serviceDurationUnit;
    }

    public void setServiceDurationUnit(String serviceDurationUnit) {
        this.serviceDurationUnit = serviceDurationUnit;
    }

    public String getServiceExplain() {
        return serviceExplain;
    }

    public void setServiceExplain(String serviceExplain) {
        this.serviceExplain = serviceExplain;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceHisPrice() {
        return serviceHisPrice;
    }

    public void setServiceHisPrice(String serviceHisPrice) {
        this.serviceHisPrice = serviceHisPrice;
    }

    public Object getSysId() {
        return sysId;
    }

    public void setSysId(Object sysId) {
        this.sysId = sysId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int pIsHot) {
        isHot = pIsHot;
    }

    @Override
    public String toString() {
        return "SaleModel{" +
                "isCheck=" + isCheck +
                ", isHot=" + isHot +
                ", id='" + id + '\'' +
                ", dataStatus=" + dataStatus +
                ", isPublish=" + isPublish +
                ", sn=" + sn +
                ", serviceDuration=" + serviceDuration +
                ", serviceType=" + serviceType +
                ", serviceTypeName='" + serviceTypeName + '\'' +
                ", attr1=" + attr1 +
                ", owner=" + owner +
                ", priceUnit='" + priceUnit + '\'' +
                ", serviceDurationUnit='" + serviceDurationUnit + '\'' +
                ", serviceExplain='" + serviceExplain + '\'' +
                ", servicePrice=" + servicePrice +
                ", serviceHisPrice=" + serviceHisPrice +
                ", sysId=" + sysId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
