package com.kongrongqi.shopmall.modules.model;

import java.util.List;

/**
 * 创建日期：2017/6/5 0005 on 12:06
 * 作者:penny
 */
public class PictureModel extends BaseModel{
    /**
     * bizCode : 0
     * code : 200
     * data : ["image/upload.png","image/download.png"]
     * msg :
     */

    private int bizCode;
    private int code;
    private String msg;
    private List<String> data;

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "PictureModel{" +
                "bizCode=" + bizCode +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
