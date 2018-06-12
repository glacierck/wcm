package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/5/31 0031 on 11:31
 * 作者:penny
 */
public class WechatGrounpRequest extends BaseRequest{
    String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "WechatGrounpRequest{" +
                "groupName='" + groupName + '\'' +
                '}';
    }
}
