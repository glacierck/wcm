package com.kongrongqi.shopmall.modules.model;

import android.databinding.Bindable;

/**
 * 创建日期：2017/5/31 0031 on 11:58
 * 作者:penny
 */
public class WechatResultsBean extends BaseModel{
    /**
     * currentPage : 1
     * pageSize : 10
     * groupInfoId : 257388545703031
     * accountSearchCondition : null
     * groupName : 测试群
     * groupCreatorAccount : null
     * groupCreatorName : null
     * currentSize : null
     * targetSize : null
     * targetGroupAdminAccount : null
     * targetGroupOwnerAccount : null
     * targetGroupMessage : null
     * targetGroupRemark : null
     * groupAccounts : null
     * taskDefinitionId : null
     */

    private int currentPage;
    private int pageSize;
    /**
     * 群id
     */
    private String groupInfoId;
    /**
     * 群成员搜索条件
     */
    private String accountSearchCondition;
    /**
     * 群名
     */
    private String groupName;
    /**
     * 建群人账号
     */
    private String groupCreatorAccount;
    /**
     * 建群人名
     */
    private String groupCreatorName;
    /**
     * 群成员
     */
    private String currentSize;
    /**
     *
     */
    private String targetSize;
    private String targetGroupAdminAccount;
    private String targetGroupOwnerAccount;
    /**
     * 拉群消息
     */
    private String targetGroupMessage;
    /**
     * 群公告
     */
    private String targetGroupRemark;
    /***
     * 所有成员
     */
    private String groupAccounts;
    /**
     * 项目ID
     */
    private String taskDefinitionId;

    @Bindable
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Bindable
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Bindable
    public String getGroupInfoId() {
        return groupInfoId;
    }

    public void setGroupInfoId(String groupInfoId) {
        this.groupInfoId = groupInfoId;
    }

    @Bindable
    public String getAccountSearchCondition() {
        return accountSearchCondition;
    }

    public void setAccountSearchCondition(String accountSearchCondition) {
        this.accountSearchCondition = accountSearchCondition;
    }

    @Bindable
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Bindable
    public String getGroupCreatorAccount() {
        return groupCreatorAccount;
    }

    @Bindable
    public void setGroupCreatorAccount(String groupCreatorAccount) {
        this.groupCreatorAccount = groupCreatorAccount;
    }

    @Bindable
    public String getGroupCreatorName() {
        return groupCreatorName;
    }

    public void setGroupCreatorName(String groupCreatorName) {
        this.groupCreatorName = groupCreatorName;
    }

    @Bindable
    public String getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(String currentSize) {
        this.currentSize = currentSize;
    }

    @Bindable
    public String getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(String targetSize) {
        this.targetSize = targetSize;
    }

    @Bindable
    public Object getTargetGroupAdminAccount() {
        return targetGroupAdminAccount;
    }

    public void setTargetGroupAdminAccount(String targetGroupAdminAccount) {
        this.targetGroupAdminAccount = targetGroupAdminAccount;
    }

    @Bindable
    public String getTargetGroupOwnerAccount() {
        return targetGroupOwnerAccount;
    }

    public void setTargetGroupOwnerAccount(String targetGroupOwnerAccount) {
        this.targetGroupOwnerAccount = targetGroupOwnerAccount;
    }

    @Bindable
    public String getTargetGroupMessage() {
        return targetGroupMessage;
    }

    public void setTargetGroupMessage(String targetGroupMessage) {
        this.targetGroupMessage = targetGroupMessage;
    }

    @Bindable
    public String getTargetGroupRemark() {
        return targetGroupRemark;
    }

    public void setTargetGroupRemark(String targetGroupRemark) {
        this.targetGroupRemark = targetGroupRemark;
    }

    @Bindable
    public String getGroupAccounts() {
        return groupAccounts;
    }

    public void setGroupAccounts(String groupAccounts) {
        this.groupAccounts = groupAccounts;
    }

    @Bindable
    public String getTaskDefinitionId() {
        return taskDefinitionId;
    }

    public void setTaskDefinitionId(String taskDefinitionId) {
        this.taskDefinitionId = taskDefinitionId;
    }
}
