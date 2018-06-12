package com.kongrongqi.shopmall.modules.model;

import com.google.gson.annotations.SerializedName;

/**
 * 创建日期：2017/6/3 0003 on 19:10
 * 作者:penny
 */
public class FansModel extends BaseModel{

    /**
     * fansName : 服装销售
     * id : 1
     * owner :
     * price : 200
     * userId :
     */



    private String fansName;
    private String id;
    private String owner;
    private String price;
    private String userId;
    private int dataStatus;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getFansName() {
        return fansName;
    }

    public void setFansName(String fansName) {
        this.fansName = fansName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }


}
