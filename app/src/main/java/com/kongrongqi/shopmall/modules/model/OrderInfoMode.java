package com.kongrongqi.shopmall.modules.model;

import java.math.BigDecimal;

/**
 * 创建日期：2017/5/31 0031 on 17:03
 * 作者:penny
 */
public class OrderInfoMode extends BaseModel {


    /**
     * createTimeFormat :
     * id : 202847964655941
     * isBill : 0
     * isUsed : 0
     * orderName : string
     * orderNo : 201706031707354280334611100
     * owner : string
     * price : 1
     * receiveAddress : string
     * receiveAddressId : string
     * status : 10
     * type : 11
     * userId : 111
     * wechatServiceId : string
     */

    private String id;

//    @ApiModelProperty(value = "用户ID" )
    private String userId;

//    @ApiModelProperty(value = "订单编号" )
    private String orderNo;

//    @ApiModelProperty(value = "订单名称" )
    private String orderName;

//    @ApiModelProperty(value = "用户账号服务表ID" )
    private String wechatServiceId;

//    @ApiModelProperty(value = "服务价格" )
    private BigDecimal price;

//    @ApiModelProperty(value = "支付类型 :8 微信 ; 11 支付宝" )
    private Integer type;

//    @ApiModelProperty(value = "用户收货地址ID" )
    private String receiveAddressId;

//    @ApiModelProperty(value = "用户收货地址" )
    private String receiveAddress;

//    @ApiModelProperty(value = "订单状态 10:未提交 11:待支付; 12:已完成; 20:已作废;" )
    private Integer status;

//    @ApiModelProperty(value = "是否已使用 1 已使用 0 未使用" )
//    @NotColumn(value = "")
    private Integer isUsed=0;

//    @ApiModelProperty(value = "创建时间格式化" )
//    @NotColumn(value = "")
    private String createTimeFormat;

//    @ApiModelProperty(value = "是否已开票" )
    private Integer isBill;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsBill() {
        return isBill;
    }

    public void setIsBill(Integer isBill) {
        this.isBill = isBill;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getWechatServiceId() {
        return wechatServiceId;
    }

    public void setWechatServiceId(String wechatServiceId) {
        this.wechatServiceId = wechatServiceId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(String receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserOrder [orderNo=" + orderNo + ", orderName=" + orderName + ", wechatServiceId=" + wechatServiceId
                + ", price=" + price + ", type=" + type + ", receiveAddressId=" + receiveAddressId + ", receiveAddress="
                + receiveAddress + ", status=" + status + "]";
    }
}
