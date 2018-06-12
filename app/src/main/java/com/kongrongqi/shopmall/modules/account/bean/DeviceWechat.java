package com.kongrongqi.shopmall.modules.account.bean;

import android.databinding.Bindable;

import com.kongrongqi.shopmall.modules.model.BaseModel;

/**
 * 设备关联号槽信息
 */
public class DeviceWechat extends BaseModel {
    private Long createTime;
    private Integer dataStatus;
    private String failureReasons;
    private Integer hasGroove;
    private String id;
    private String name;
    private String owner;
    private Integer pageNumber;
    private Integer pageSize;
    private String password;
    private Integer sn;
    private String sort;
    private Integer state;
    private String sysId;
    private Integer totalRecord;
    private Long updateTime;
    private String userDeviceId;
    private String userId;
    private String oldWechatNo; //老微信号
    private String wechatNo; //用户ID

    private Integer serviceNum; //所有服务数

    private int todayFinish;//0 没完成  1 完成
    private String tomorrowStartTime;//明天执行时间

    private String serviceMsg;

    private Integer deviceState;//设备状态  1 正常  2 异常

    private Integer hasTodayInProgress;

    private String msg;//账号消息

    private Integer auditStatus; //审核状态 ; 1 未审核 2 审核中 3 审核通过  （4 脚本响应超时 5 审核失败（ 微信在设备未登录，微信账号密码错误，封号） 6 未绑定银行卡，未实名）

    private String fansNum;//好友数量

    private String todayFansNum ;//今日新增友量

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getTodayFansNum() {
        return todayFansNum;
    }

    public void setTodayFansNum(String todayFansNum) {
        this.todayFansNum = todayFansNum;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(Integer serviceNum) {
        this.serviceNum = serviceNum;
    }

    public Integer getHasTodayInProgress() {
        return hasTodayInProgress;
    }

    public void setHasTodayInProgress(Integer hasTodayInProgress) {
        this.hasTodayInProgress = hasTodayInProgress;
    }

    public Integer getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(Integer deviceState) {
        this.deviceState = deviceState;
    }

    public String getServiceMsg() {
        return serviceMsg;
    }

    public void setServiceMsg(String serviceMsg) {
        this.serviceMsg = serviceMsg;
    }

    //页面 元素 值
    private String accountName;//微信号
    private String serviceCount;//服务数
    private String serviceStatus;//状态
    private String bntText;//按钮文字
    private int bntStatus;//按钮状态
    private int accountStatus;//账号状态  0 为默认无状态  只有当bntStatus= 5.查看原因(1脚本响应超时，2登录异常，3未绑定银行卡，4、账号异常) 才又意义


    public String getOldWechatNo() {
        return oldWechatNo;
    }

    public void setOldWechatNo(String oldWechatNo) {
        this.oldWechatNo = oldWechatNo;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(String serviceCount) {
        this.serviceCount = serviceCount;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getBntText() {
        return bntText;
    }

    public void setBntText(String bntText) {
        this.bntText = bntText;
    }

    public int getBntStatus() {
        return bntStatus;
    }

    public void setBntStatus(int bntStatus) {
        this.bntStatus = bntStatus;
    }

    public int getTodayFinish() {
        return todayFinish;
    }

    public void setTodayFinish(int todayFinish) {
        this.todayFinish = todayFinish;
    }

    public String getTomorrowStartTime() {
        return tomorrowStartTime;
    }

    public void setTomorrowStartTime(String tomorrowStartTime) {
        this.tomorrowStartTime = tomorrowStartTime;
    }

    @Bindable
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Bindable
    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Bindable
    public String getFailureReasons() {
        return failureReasons;
    }

    public void setFailureReasons(String failureReasons) {
        this.failureReasons = failureReasons;
    }

    @Bindable
    public Integer getHasGroove() {
        return hasGroove;
    }

    public void setHasGroove(Integer hasGroove) {
        this.hasGroove = hasGroove;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Bindable
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Bindable
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    @Bindable
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Bindable
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Bindable
    public String getSysId() {
        return sysId;
    }

    @Bindable
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    @Bindable
    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
}
