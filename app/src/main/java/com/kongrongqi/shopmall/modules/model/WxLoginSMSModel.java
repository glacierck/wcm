package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/3 0003 on 19:10
 * 作者:penny
 */
public class WxLoginSMSModel extends BaseModel{

    private String wechatNo;
    private String smsCode;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
