package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/12 0012 on 17:55
 * 作者:penny
 */
public class UpLoadExcelModel extends BaseModel {


    /**
     * condition :
     * content : 我有数据灌粉
     * contentName : 服务内容
     * count : 0
     * createTimeFormat :
     * deviceWechatId :
     * duration :
     * durationName : 服务周期
     * durationUnit :
     * fansNum : 0
     * groupId :
     * groupName :
     * id : 6
     * machineCode :
     * name :
     * owner :
     * price : 0.029999999329447746
     * progress :
     * progressRate : 0
     * status : 0
     * taskId :
     * todayFansNum : 0
     * total : 3
     * type : 0
     * unUsedNum : 0
     * usedRate :
     * userDeviceName :
     * userId :
     * userOrderId :
     * wechatNo :
     * wechatServiceId :
     */

    private String condition;
    private String content;
    private String contentName;
    private int count;
    private String createTimeFormat;
    private String deviceWechatId;
    private String duration;
    private String durationName;
    private String durationUnit;
    private int fansNum;
    private String groupId;
    private String groupName;
    private String id;
    private String machineCode;
    private String name;
    private String owner;
    private double price;
    private String progress;
    private int progressRate;
    private int status;
    private String taskId;
    private int todayFansNum;
    private int total;
    private int type;
    private int unUsedNum;
    private String usedRate;
    private String userDeviceName;
    private String userId;
    private String userOrderId;
    private String wechatNo;
    private String wechatServiceId;

    private int durationFansNum;//服务周期每日需加友量

    public int getDurationFansNum() {
        return durationFansNum;
    }

    public void setDurationFansNum(int durationFansNum) {
        this.durationFansNum = durationFansNum;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }

    public String getDeviceWechatId() {
        return deviceWechatId;
    }

    public void setDeviceWechatId(String deviceWechatId) {
        this.deviceWechatId = deviceWechatId;
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

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(int progressRate) {
        this.progressRate = progressRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getTodayFansNum() {
        return todayFansNum;
    }

    public void setTodayFansNum(int todayFansNum) {
        this.todayFansNum = todayFansNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUnUsedNum() {
        return unUsedNum;
    }

    public void setUnUsedNum(int unUsedNum) {
        this.unUsedNum = unUsedNum;
    }

    public String getUsedRate() {
        return usedRate;
    }

    public void setUsedRate(String usedRate) {
        this.usedRate = usedRate;
    }

    public String getUserDeviceName() {
        return userDeviceName;
    }

    public void setUserDeviceName(String userDeviceName) {
        this.userDeviceName = userDeviceName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getWechatServiceId() {
        return wechatServiceId;
    }

    public void setWechatServiceId(String wechatServiceId) {
        this.wechatServiceId = wechatServiceId;
    }

    @Override
    public String toString() {
        return "UpLoadExcelModel{" +
                "condition='" + condition + '\'' +
                ", content='" + content + '\'' +
                ", contentName='" + contentName + '\'' +
                ", count=" + count +
                ", createTimeFormat='" + createTimeFormat + '\'' +
                ", deviceWechatId='" + deviceWechatId + '\'' +
                ", duration='" + duration + '\'' +
                ", durationName='" + durationName + '\'' +
                ", durationUnit='" + durationUnit + '\'' +
                ", fansNum=" + fansNum +
                ", groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", id='" + id + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", price=" + price +
                ", progress='" + progress + '\'' +
                ", progressRate=" + progressRate +
                ", status=" + status +
                ", taskId='" + taskId + '\'' +
                ", todayFansNum=" + todayFansNum +
                ", total=" + total +
                ", type=" + type +
                ", unUsedNum=" + unUsedNum +
                ", usedRate='" + usedRate + '\'' +
                ", userDeviceName='" + userDeviceName + '\'' +
                ", userId='" + userId + '\'' +
                ", userOrderId='" + userOrderId + '\'' +
                ", wechatNo='" + wechatNo + '\'' +
                ", wechatServiceId='" + wechatServiceId + '\'' +
                '}';
    }
}
