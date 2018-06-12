package com.kongrongqi.shopmall.modules.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 创建日期：2017/5/31 0031 on 11:51
 * 作者:penny
 */
public class WechatGrounpModel extends BaseModel {


    /**
     * id : 1369079234561543
     * currentSize : null
     * isMarket : 1
     * targetSize : null
     * accountSearchCondition : null
     * creator : null
     * groupAccounts : null
     * groupCreatorAccount : null
     * groupCreatorName : null
     * groupName : 空容器-测试1群
     * groupWorth : 998
     * targetGroupAdminAccount : null
     * targetGroupMessage : null
     * targetGroupOwnerAccount : null
     * targetGroupRemark : null
     * taskDefinitionId : null
     * createTime : 1496620800000
     * updateTime : 1496620800000
     */

    private String id ;
    private int currentSize ;
    private int isMarket ;
    private int targetSize ;
    private String accountSearchCondition ;
    private String creator ;
    private String groupAccounts ;
    private String groupCreatorAccount ;
    private String groupCreatorName ;
    private String groupName ;
    private String groupWorth ;
    private String targetGroupAdminAccount ;
    private String targetGroupMessage ;
    private String targetGroupOwnerAccount ;
    private String targetGroupRemark ;
    private String taskDefinitionId ;
    private long createTime ;
    private long updateTime ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public Integer getIsMarket() {
        return isMarket;
    }

    public void setIsMarket(Integer isMarket) {
        this.isMarket = isMarket;
    }

    public Integer getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(Integer targetSize) {
        this.targetSize = targetSize;
    }

    public String getAccountSearchCondition() {
        return accountSearchCondition;
    }

    public void setAccountSearchCondition(String accountSearchCondition) {
        this.accountSearchCondition = accountSearchCondition;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getGroupAccounts() {
        return groupAccounts;
    }

    public void setGroupAccounts(String groupAccounts) {
        this.groupAccounts = groupAccounts;
    }

    public String getGroupCreatorAccount() {
        return groupCreatorAccount;
    }

    public void setGroupCreatorAccount(String groupCreatorAccount) {
        this.groupCreatorAccount = groupCreatorAccount;
    }

    public String getGroupCreatorName() {
        return groupCreatorName;
    }

    public void setGroupCreatorName(String groupCreatorName) {
        this.groupCreatorName = groupCreatorName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupWorth() {
        return groupWorth;
    }

    public void setGroupWorth(String groupWorth) {
        this.groupWorth = groupWorth;
    }

    public String getTargetGroupAdminAccount() {
        return targetGroupAdminAccount;
    }

    public void setTargetGroupAdminAccount(String targetGroupAdminAccount) {
        this.targetGroupAdminAccount = targetGroupAdminAccount;
    }

    public String getTargetGroupMessage() {
        return targetGroupMessage;
    }

    public void setTargetGroupMessage(String targetGroupMessage) {
        this.targetGroupMessage = targetGroupMessage;
    }

    public String getTargetGroupOwnerAccount() {
        return targetGroupOwnerAccount;
    }

    public void setTargetGroupOwnerAccount(String targetGroupOwnerAccount) {
        this.targetGroupOwnerAccount = targetGroupOwnerAccount;
    }

    public String getTargetGroupRemark() {
        return targetGroupRemark;
    }

    public void setTargetGroupRemark(String targetGroupRemark) {
        this.targetGroupRemark = targetGroupRemark;
    }

    public String getTaskDefinitionId() {
        return taskDefinitionId;
    }

    public void setTaskDefinitionId(String taskDefinitionId) {
        this.taskDefinitionId = taskDefinitionId;
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

    @Override
    public String toString() {
        return "WechatGrounpModel{" +
                "id='" + id + '\'' +
                ", currentSize=" + currentSize +
                ", isMarket=" + isMarket +
                ", targetSize=" + targetSize +
                ", accountSearchCondition='" + accountSearchCondition + '\'' +
                ", creator='" + creator + '\'' +
                ", groupAccounts='" + groupAccounts + '\'' +
                ", groupCreatorAccount='" + groupCreatorAccount + '\'' +
                ", groupCreatorName='" + groupCreatorName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupWorth=" + groupWorth +
                ", targetGroupAdminAccount='" + targetGroupAdminAccount + '\'' +
                ", targetGroupMessage='" + targetGroupMessage + '\'' +
                ", targetGroupOwnerAccount='" + targetGroupOwnerAccount + '\'' +
                ", targetGroupRemark='" + targetGroupRemark + '\'' +
                ", taskDefinitionId='" + taskDefinitionId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

