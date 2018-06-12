package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/6/1 0001 on 10:32
 * 作者:penny
 */
public class OrderInfoRequest extends BaseRequest {


    /**
     * userId  : 0
     * attach : string
     * companyId : 0
     * money : string
     * notifyUrl : string
     * openId : string
     * platformId  : 0
     * remark  : string
     * orderNo  : string
     * payNotifyUrl : string
     * paymentConfigId : 11
     * userOrderId  :
     */


    private String attach;
    private int companyId = 5;
    private String money;
    private String notifyUrl;
    private String openId;
    private int platformId = 0;
    private String remark;
    private String orderNo;
    private String payNotifyUrl;
    private int paymentConfigId;
    private String userOrderId;


    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public int getPaymentConfigId() {
        return paymentConfigId;
    }

    public void setPaymentConfigId(int paymentConfigId) {
        this.paymentConfigId = paymentConfigId;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }
}
