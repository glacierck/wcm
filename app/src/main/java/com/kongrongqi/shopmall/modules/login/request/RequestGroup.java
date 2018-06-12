package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/6/8 0008 on 11:04
 * 作者:penny
 */
public class RequestGroup extends BaseRequest {
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "RequestGroup{" +
                "groupName='" + groupName + '\'' +
                '}';
    }
}
