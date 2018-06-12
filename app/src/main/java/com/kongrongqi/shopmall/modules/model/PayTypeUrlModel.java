package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/5/31 0031 on 15:18
 * 作者:penny
 */
public class PayTypeUrlModel extends BaseModel{

    /**
     * payJumpUrl : http://192.168.1.168:80/dis-shop-api/pay/getPayInfoByOrderNo.do
     * notifyUrl : http://192.168.1.168:8080/dis-shop-api/pay/updateOrderFromAli.do
     * paymentConfigId : 11
     * paymentGateway : 11
     * paymentThirdparty : 支付宝
     */

    private String payJumpUrl;
    private String notifyUrl;
    private int paymentConfigId;
    private int paymentGateway;
    private String paymentThirdparty;

    public String getPayJumpUrl() {
        return payJumpUrl;
    }

    public void setPayJumpUrl(String payJumpUrl) {
        this.payJumpUrl = payJumpUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getPaymentConfigId() {
        return paymentConfigId;
    }

    public void setPaymentConfigId(int paymentConfigId) {
        this.paymentConfigId = paymentConfigId;
    }

    public int getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(int paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getPaymentThirdparty() {
        return paymentThirdparty;
    }

    public void setPaymentThirdparty(String paymentThirdparty) {
        this.paymentThirdparty = paymentThirdparty;
    }

    @Override
    public String toString() {
        return "PayTypeUrlModel{" +
                "payJumpUrl='" + payJumpUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", paymentConfigId=" + paymentConfigId +
                ", paymentGateway=" + paymentGateway +
                ", paymentThirdparty='" + paymentThirdparty + '\'' +
                '}';
    }
}
