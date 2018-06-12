package com.kongrongqi.shopmall.modules.login.request;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * Created on 2017/7/24 0024.
 * by penny
 */

public class SaleListRequest extends BaseRequest{
    int serverType;

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int pServerType) {
        serverType = pServerType;
    }

    @Override
    public String toString() {
        return "SaleListRequest{" +
                "serverType=" + serverType +
                '}';
    }
}
