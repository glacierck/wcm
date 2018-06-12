package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/6/3 0003 on 15:04
 * 作者:penny
 */
public class IvoiceRequest extends BaseRequest {

    /**
     * createTime : 2017-06-03T06:20:26.112Z
     * createTimeFormat : string
     * dataStatus : 0
     * id : string
     * isBill : 1
     * isUsed : 0
     * orderName : string
     * orderNo : string
     * owner : string
     * pageNumber : 0
     * pageSize : 0
     * price : 0
     * receiveAddress : string
     * receiveAddressId : string
     * sort : string
     * status : 0
     * sysId : string
     * totalRecord : 0
     * type : 0
     * updateTime : 2017-06-03T06:20:26.112Z
     * userId : 111
     * wechatServiceId : string
     */

    private String createTime;
    private String createTimeFormat;
    private int dataStatus;
    private String id;
    private int isBill;
    private int isUsed;
    private String orderName;
    private String orderNo;
    private String owner;
    private int pageNumber;
    private int pageSize;
    private int price;
    private String receiveAddress;
    private String receiveAddressId;
    private String sort;
    private int status;
    private String sysId;
    private int totalRecord;
    private int type;
    private String updateTime;

    private String wechatServiceId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsBill() {
        return isBill;
    }

    public void setIsBill(int isBill) {
        this.isBill = isBill;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(String receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWechatServiceId() {
        return wechatServiceId;
    }

    public void setWechatServiceId(String wechatServiceId) {
        this.wechatServiceId = wechatServiceId;
    }

    @Override
    public String toString() {
        return "IvoiceRequest{" +
                "createTime='" + createTime + '\'' +
                ", createTimeFormat='" + createTimeFormat + '\'' +
                ", dataStatus=" + dataStatus +
                ", id='" + id + '\'' +
                ", isBill=" + isBill +
                ", isUsed=" + isUsed +
                ", orderName='" + orderName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", owner='" + owner + '\'' +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", price=" + price +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", receiveAddressId='" + receiveAddressId + '\'' +
                ", sort='" + sort + '\'' +
                ", status=" + status +
                ", sysId='" + sysId + '\'' +
                ", totalRecord=" + totalRecord +
                ", type=" + type +
                ", updateTime='" + updateTime + '\'' +
                ", wechatServiceId='" + wechatServiceId + '\'' +
                '}';
    }
}
