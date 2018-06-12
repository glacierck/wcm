package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/3 0003 on 14:53
 * 作者:penny
 */
public class IvoiceModel extends BaseModel {

    /**
     * createTimeFormat :
     * id : 1
     * isBill : 1
     * isUsed : 0
     * orderName : 灌粉服务A
     * orderNo : 123456qqq
     * owner :
     * price : 300
     * receiveAddress :
     * receiveAddressId :
     * status : 11
     * type : 11
     * userId : 111
     * wechatServiceId : 5
     */

    private String createTimeFormat;
    private String id;
    private int isBill;
    private int isUsed;
    private String orderName;
    private String orderNo;
    private String owner;
    private String price;
    private String receiveAddress;
    private String receiveAddressId;
    private int status;
    private int type;
    private String userId;
    private String wechatServiceId;

    private boolean check;


    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWechatServiceId() {
        return wechatServiceId;
    }

    public void setWechatServiceId(String wechatServiceId) {
        this.wechatServiceId = wechatServiceId;
    }

    @Override
    public String toString() {
        return "IvoiceModel{" +
                "createTimeFormat='" + createTimeFormat + '\'' +
                ", id='" + id + '\'' +
                ", isBill=" + isBill +
                ", isUsed=" + isUsed +
                ", orderName='" + orderName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", owner='" + owner + '\'' +
                ", price=" + price +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", receiveAddressId='" + receiveAddressId + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", userId='" + userId + '\'' +
                ", wechatServiceId='" + wechatServiceId + '\'' +
                '}';
    }
}
