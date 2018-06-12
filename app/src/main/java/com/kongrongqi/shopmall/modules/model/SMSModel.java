package com.kongrongqi.shopmall.modules.model;

import android.databinding.Bindable;

/**
 * 创建日期：2017/5/23 0023 on 14:05
 * 作者:penny
 */
public class SMSModel extends BaseModel {

    /**
     * smsCode : 8094
     */

    private String smsCode;

    @Bindable
    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    @Override
    public String toString() {
        return "SMSModel{" +
                "smsCode='" + smsCode + '\'' +
                '}';
    }
}
