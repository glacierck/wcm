package com.kongrongqi.shopmall.modules.login.request;

import android.databinding.Bindable;

import com.kongrongqi.shopmall.http.BaseRequest;

/**
 * 创建日期：2017/5/25 0025 on 14:59
 * 作者:penny
 */
public class RequestEmpty extends BaseRequest {
    public int status;

    public int currentPage = 1;

    public int pageSize = 20;



    @Bindable
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Bindable
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Bindable
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestEmpty{" +
                "status=" + status +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
