package com.kongrongqi.shopmall.modules.model;

import android.databinding.Bindable;

/**
 * 创建日期：2017/5/25 0025 on 15:15
 * 作者:penny
 */
public class ServiceListModel extends BaseModel {
    /**
     * content :
     * contentName :
     * createTime : 1495530663000
     * dataStatus : 0
     * duration :
     * durationName :
     * id : 2
     * name : 账号号槽
     * owner :
     * pageNumber : 1
     * pageSize : 10
     * price : 3000
     * sn : 1
     * sort :
     * status : 1
     * sysId :
     * totalRecord : 0
     * updateTime : null
     * userId :
     */
    /**
     * 服务内容名称
     */
    private String content;
    private String contentName;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 数据状态
     */
    private int dataStatus;
    private String duration;
    private String durationName;
    private String durationUnit;
    private String id;
    private String name;
    private String owner;
    private int pageNumber;
    private int pageSize;
    private String price;
    private int sn;
    private String sort;
    private int status;
    private String sysId;
    private int totalRecord;
    private Object updateTime;
    private String userId;
    /**
     * 产品类型   2灌粉服务A   3灌粉服务B  4入群服务  5养号服务
     */
    private int type;

    @Bindable
    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    @Bindable
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Bindable
    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    @Bindable
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Bindable
    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Bindable
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Bindable
    public String getDurationName() {
        return durationName;
    }

    public void setDurationName(String durationName) {
        this.durationName = durationName;
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
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Bindable
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Bindable
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Bindable
    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
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
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Bindable
    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    @Bindable
    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    @Bindable
    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ServiceListModel{" +
                "content='" + content + '\'' +
                ", contentName='" + contentName + '\'' +
                ", createTime=" + createTime +
                ", dataStatus=" + dataStatus +
                ", duration='" + duration + '\'' +
                ", durationName='" + durationName + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", price=" + price +
                ", sn=" + sn +
                ", sort='" + sort + '\'' +
                ", status=" + status +
                ", sysId='" + sysId + '\'' +
                ", totalRecord=" + totalRecord +
                ", updateTime=" + updateTime +
                ", userId='" + userId + '\'' +
                '}';
    }
}
