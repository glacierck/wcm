package com.kongrongqi.shopmall.http;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * 创建日期：2017/5/25 0025 on 14:35
 * 作者:penny
 */
public class BaseRequest extends BaseObservable implements Serializable {
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
