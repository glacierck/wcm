package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/5/31 0031 on 17:12
 * 作者:penny
 */
public class OrderNumRequest extends BaseRequest {

    /**
     * userId  : string
     * content  : string
     * contentName  : 2017-06-03T07:23:02.816Z
     * deviceWechatId  : 0
     * durationName  : string
     * duration : string
     * durationName : string
     * durationUnit : string
     * price  : 0
     * id  : string
     * owner : string
     * name  : string
     * paymentType  : 0
     * receiveAddress : string
     * receiveAddressId : string
     * type  : string
     */

    /**
     * 群ID
     */
    private String relationId;

    /**
     * 服务内容
     */
    private String content;
    /**
     * contentName
     */
    private String contentName;

    /**
     * deviceWechatId  可以为空
     */
    private String deviceWechatId;

    private String duration;
    /**
     * 服务周期次数名称  可以为空
     */
    private String durationName;
    /**
     * 服务周期次数单位 可以为空
     */
    private String durationUnit;
    /**
     * 服务价格
     */
    private String price;
    /**
     * 服务对象ID
     */
    private String id;
    /**
     * 服务名称
     */
    private String name;
    /**
     * 支付类型 8 微信 11 支付宝
     */
    private int paymentType;
    /**
     * 用户收货地址 可以为空   |  绑定设备必须传
     */
    private String receiveAddress;
    /**
     * 用户收货地址ID 可以为空 |  绑定设备必须传
     */
    private String receiveAddressId;
    /**
     * 服务类型  1 账号号槽 2 灌粉服务A 3 灌粉服务B 4 入群服务 5 养号服务
     */
    private int type;

    /**
     *过期天数
     */
    private String expireDays;

    private String fansTypeId;//加友C 服务类型id，分割

    private String fansTypeName="";//加友C 服务类型名称，分割

    private String groupName;//群名称

    private int entrance;//入口 设备 ACCOUNT = 0x001;设备   进程 TASK = 0x002;
    private String wechatNo;//微信号

    private int durationFansNum;//服务周期每日需加友量

    private int splitCopies;//划分的份数

//    address  地址 category 行业 gender 性别
    private String address;//地址
    private String category;//行业
    private String gender;//性别


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSplitCopies() {
        return splitCopies;
    }

    public void setSplitCopies(int splitCopies) {
        this.splitCopies = splitCopies;
    }

    public int getDurationFansNum() {
        return durationFansNum;
    }

    public void setDurationFansNum(int durationFansNum) {
        this.durationFansNum = durationFansNum;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    public String getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(String pExpireDays) {
        expireDays = pExpireDays;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getFansTypeName() {
        return fansTypeName;
    }

    public void setFansTypeName(String fansTypeName) {
        this.fansTypeName = fansTypeName;
    }

    public String getFansTypeId() {
        return fansTypeId;
    }

    public void setFansTypeId(String fansTypeId) {
        this.fansTypeId = fansTypeId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getDeviceWechatId() {
        return deviceWechatId;
    }

    public void setDeviceWechatId(String deviceWechatId) {
        this.deviceWechatId = deviceWechatId;
    }

    public String getDurationName() {
        return durationName;
    }

    public void setDurationName(String durationName) {
        this.durationName = durationName;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderNumRequest{" +
                "relationId='" + relationId + '\'' +
                ", content='" + content + '\'' +
                ", contentName='" + contentName + '\'' +
                ", deviceWechatId='" + deviceWechatId + '\'' +
                ", duration='" + duration + '\'' +
                ", durationName='" + durationName + '\'' +
                ", durationUnit='" + durationUnit + '\'' +
                ", price='" + price + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", paymentType=" + paymentType +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", receiveAddressId='" + receiveAddressId + '\'' +
                ", type=" + type +
                ", expireDays='" + expireDays + '\'' +
                ", fansTypeId='" + fansTypeId + '\'' +
                ", fansTypeName='" + fansTypeName + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
