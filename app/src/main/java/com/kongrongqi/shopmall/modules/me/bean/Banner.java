package com.kongrongqi.shopmall.modules.me.bean;

import com.kongrongqi.shopmall.modules.model.BaseModel;

/**
 * 交易记录
 */
public class Banner extends BaseModel {
    private String image;//Banner Url
    private Integer type;// 1 跳url 内开   2 跳url 外开   3 跳内部app  4 跳外包app
    private String url; //跳转的url

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
