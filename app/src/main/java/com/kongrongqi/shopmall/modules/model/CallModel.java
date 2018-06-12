package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/3 0003 on 19:10
 * 作者:penny
 */
public class CallModel extends BaseModel{
    private String callSample;//样例：XXXX有限公司，麻烦通过
    private String callTitle;//公司名+
    private String callTxt;//麻烦通过（点击编辑）
    private Integer callType;//类型
    private String id;
    private Integer isSelect;
    private String owner;
    private String sn;
    private String userId;

    private String callTxtInput;//用户输入数据

    public String getCallTxtInput() {
        return callTxtInput;
    }

    public void setCallTxtInput(String callTxtInput) {
        this.callTxtInput = callTxtInput;
    }

    public String getCallSample() {
        return callSample;
    }

    public void setCallSample(String callSample) {
        this.callSample = callSample;
    }

    public String getCallTitle() {
        return callTitle;
    }

    public void setCallTitle(String callTitle) {
        this.callTitle = callTitle;
    }

    public String getCallTxt() {
        return callTxt;
    }

    public void setCallTxt(String callTxt) {
        this.callTxt = callTxt;
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
