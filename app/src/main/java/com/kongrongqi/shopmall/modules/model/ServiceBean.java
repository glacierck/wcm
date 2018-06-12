package com.kongrongqi.shopmall.modules.model;

import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;

/**
 * Created by Administrator on 2017/6/26 0026.
 *
 * 服务列表  实体
 */

public class ServiceBean extends BaseModel{

    /**
     * id : 5
     * dataStatus : 0
     * sn : 1
     * status : 1
     * type : 2
     * attr1 : null
     * attr2 : null
     * attr3 : null
     * content : 我没有数据灌粉
     * contentName : 服务内容
     * duration : 1
     * durationName : 服务周期
     * durationUnit : 天
     * name : 加友服务A
     * owner : yy
     * price : 0.01
     * sysId : null
     * createTime : 1495708353000
     * updateTime : 1497944240000
     * condition : null
     * explain : null
     * notice : null
     * frontChannle : 0
     */

    private int validDay;
    private String id;
    private int dataStatus;
    private int sn;
    private int status;//状态 1 正常购买 2 暂停
    private int type; // 2 加友A
    private String attr1;
    private String attr2;
    private String attr3;
    private String content;//服务内容
    private String contentName;
    private String duration;//服务周期
    private String durationText;//服务周期
    private String durationName;
    private String durationUnit;//服务周期 单位
    private String name;
    private String owner;
    private String price;//服务价格

    private String priceText;//服务价格
    private String sysId;
    private long createTime;
    private long updateTime;
    private String condition;//服务条件
    private String explain;//服务说明
    private String notice;//注意事项
    private String frontChannle;//
    private String range;//服务范围
    private Boolean isCheck = false;

    private String buttonText;

    private int entrance= ServiceSuper.TASK;//入口 设备 ACCOUNT = 0x001;设备   进程 TASK = 0x002;
    private String deviceWechatId;//号槽id
    private String wechatNo;//微信号


    //    address  地址 category 行业 gender 性别
    private String address;//地址
    private String category;//行业
    private String gender;//性别

    public String getAddressAll() {
        return addressAll;
    }

    public void setAddressAll(String addressAll) {
        this.addressAll = addressAll;
    }

    private String addressAll;//地址

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

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getDeviceWechatId() {
        return deviceWechatId;
    }

    public void setDeviceWechatId(String deviceWechatId) {
        this.deviceWechatId = deviceWechatId;
    }

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public int getValidDay() {
        return validDay;
    }

    public void setValidDay(int pValidDay) {
        validDay = pValidDay;
    }

    public String getPriceText() {
        return priceText;
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

    private boolean isJoin= false;//是否事入群服务

    public boolean isJoin() {
        return isJoin;
    }

    public void setJoin(boolean join) {
        isJoin = join;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
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

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getFrontChannle() {
        return frontChannle;
    }

    public void setFrontChannle(String frontChannle) {
        this.frontChannle = frontChannle;
    }

    @Override
    public String toString() {
        return "ServiceBean{" +
                "validDay=" + validDay +
                ", id='" + id + '\'' +
                ", dataStatus=" + dataStatus +
                ", sn=" + sn +
                ", status=" + status +
                ", type=" + type +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3='" + attr3 + '\'' +
                ", content='" + content + '\'' +
                ", contentName='" + contentName + '\'' +
                ", duration='" + duration + '\'' +
                ", durationName='" + durationName + '\'' +
                ", durationUnit='" + durationUnit + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", price='" + price + '\'' +
                ", priceText='" + priceText + '\'' +
                ", sysId='" + sysId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", condition='" + condition + '\'' +
                ", explain='" + explain + '\'' +
                ", notice='" + notice + '\'' +
                ", frontChannle='" + frontChannle + '\'' +
                ", range='" + range + '\'' +
                ", isCheck=" + isCheck +
                ", buttonText='" + buttonText + '\'' +
                ", isJoin=" + isJoin +
                '}';
    }
}
