package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/6/5 0005 on 10:27
 * 作者:penny
 */
public class EditAddressRequest extends BaseRequest {
    String province;
    String city;
    String district;
    String address;
    String receivePhone;
    String receiver;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "EditAddressRequest{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
