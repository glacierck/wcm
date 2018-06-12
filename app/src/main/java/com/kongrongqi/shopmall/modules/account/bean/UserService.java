package com.kongrongqi.shopmall.modules.account.bean;

import com.kongrongqi.shopmall.modules.model.BaseModel;

/**
 * 设备信息
 */
public class UserService extends BaseModel {

    private String callTxt;
    private String condition;
    private String content;
    private String contentName;
    private int count;
    private String createTimeFormat;
    private String upateTimeFormat;
    private String deviceWechatId;
    private String duration;
    private String durationName;
    private String durationUnit;
    private String explain;
    private String fansNum;
    private String fansTypeId;
    private String groupId;
    private String groupName;
    private String id;
    private String machineCode;
    private String name;
    private String owner;
    private String price;
    private String progress;
    private String progressRate;
    private int status;
    private String taskId;
    private String todayFansNum;
    private int total;
    private int type;
    private int unUsedNum;
    private String usedRate;
    private String userDeviceName;
    private String userId;
    private String userOrderId;
    private String wechatNo;
    private String wechatServiceId;
    private Boolean isCheck = false;

    private int todayStatus;//（1 进行中 4 已取消 5 等待中) 2 已完成 3 暂停  6 异常

    private String expireTime; //失效时间

    private String serviceMsg; //服务消息

    private String todayMsg; //今日状态消息

    private String detail;//"KONG1伺服手机"
    private String detailName;//"赠送设备"

    private String todayServiceTime;//今日执行

    private Integer isRestart=0;//是否重启  0不显示 1显示

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getIsRestart() {
        return isRestart;
    }

    public void setIsRestart(Integer isRestart) {
        this.isRestart = isRestart;
    }

    public String getTodayServiceTime() {
        return todayServiceTime;
    }

    public void setTodayServiceTime(String todayServiceTime) {
        this.todayServiceTime = todayServiceTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getUpateTimeFormat() {
        return upateTimeFormat;
    }

    public void setUpateTimeFormat(String upateTimeFormat) {
        this.upateTimeFormat = upateTimeFormat;
    }

    public String getTodayMsg() {
        return todayMsg;
    }

    public void setTodayMsg(String todayMsg) {
        this.todayMsg = todayMsg;
    }

    public String getServiceMsg() {
        return serviceMsg;
    }

    public void setServiceMsg(String serviceMsg) {
        this.serviceMsg = serviceMsg;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getTodayStatus() {
        return todayStatus;
    }

    public void setTodayStatus(int todayStatus) {
        this.todayStatus = todayStatus;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public String getCallTxt() {
        return callTxt;
    }

    public void setCallTxt(String callTxt) {
        this.callTxt = callTxt;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getFansTypeId() {
        return fansTypeId;
    }

    public void setFansTypeId(String fansTypeId) {
        this.fansTypeId = fansTypeId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(String progressRate) {
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

    public String getTodayFansNum() {
        return todayFansNum;
    }

    public void setTodayFansNum(String todayFansNum) {
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
}
