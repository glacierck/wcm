package com.kongrongqi.shopmall.modules.model;

import com.kongrongqi.shopmall.http.BaseResponse;

/**
 * 创建日期：2017/6/1 0001 on 10:35
 * 作者:penny
 */
public class ALiPayResponse extends BaseResponse{

    /**
     * od : partner=""&seller_id=""&out_trade_no="201705311447293810900808"&subject="123"&body=""&total_fee="3000"&notify_url="http://192.168.1.168:8080/dis-shop-api/pay/updateOrderFromAli.do"&service=""&payment_type=""&_input_charset=""&it_b_pay=""&sign="3414601261f90bea1b82d637492b18c6"&sign_type="RSA"
     */

    private String authInfo;

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public String toString() {
        return "ALiPayResponse{" +
                "authInfo='" + authInfo + '\'' +
                '}';
    }
}
