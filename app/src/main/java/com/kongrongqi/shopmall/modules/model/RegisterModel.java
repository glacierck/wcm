package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/5/23 0023 on 16:04
 * 作者:penny
 */
public class RegisterModel extends BaseModel {


    /**
     * token : b1f61e1b26a4f544
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "token='" + token + '\'' +
                '}';
    }
}
