package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/1 0001 on 18:54
 * 作者:penny
 */
public class PayResultModel extends BaseModel {

    private static final long serialVersionUID = 4203082238685332205L;

    /**
     * 支付状态
     *
     * @author wangheng
     */
    public enum PayResultStatus {
        /**
         * 支付成功 *
         */
        SUCCESS,
        /**
         * 处理中 *
         */
        PROCESSING,
        /**
         * 支付失败 *
         */
        FAILED,
        /**
         * 支付被取消 *
         */
        CANCELED,
        /**
         * 网络错误 *
         */
        NETWORK_ERROR
    }

    /**
     * 支付结果状态码 *
     */
    private String resultStatus;

    /**
     * 本次操作返回的结果数据 *
     */
    private String result;

    /**
     * 提示信息 *
     */
    private String memo;


    /**
     * 得到支付状态 *
     */
    private PayResultStatus payResultStatus;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public PayResultStatus getPayResultStatus() {
        return payResultStatus;
    }

    public void setPayResultStatus(PayResultStatus payResultStatus) {
        this.payResultStatus = payResultStatus;
    }

    @Override
    public String toString() {
        return "PayResultModel{" +
                "resultStatus='" + resultStatus + '\'' +
                ", result='" + result + '\'' +
                ", memo='" + memo + '\'' +
                ", payResultStatus=" + payResultStatus +
                '}';
    }
}
