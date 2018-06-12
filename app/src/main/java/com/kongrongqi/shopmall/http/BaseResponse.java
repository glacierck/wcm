package com.kongrongqi.shopmall.http;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by penny on 2016/9/14 0014.
 *
 */
public class BaseResponse<T> extends BaseObservable implements Serializable {

    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    @SerializedName("bizCode")
    public int bizCode;

    @SerializedName("code")
    public int code;

    @SerializedName("data")
    public T data;

    @SerializedName("msg")
    public String msg;

    @Bindable
    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
//        notifyChange(BR.bizCode);
    }

    @Bindable
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
//        notifyChange(BR.code);
    }

    @Bindable
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
//        notifyChange(BR.data);
    }

    @Bindable
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
//        notifyChange(BR.msg);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }

    private void notifyChange(int propertyId) {

        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(
                this,
                propertyId
        );
    }
}

