package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/3 0003 on 19:10
 * 作者:penny
 */
public class IndustryModel extends BaseModel{

    /**
     * "childred": [],
     "dictKey": "IT",
     "dictName": "fans_industry",
     "dictText": "IT互联网",
     "id": "1",
     "owner": "1",
     "parentKey": "0",
     "sn": 0,
     "userId": ""
     */
    private String dictKey;
    private String dictName;
    private String dictText;
    private String id;
    private String owner;
    private String parentKey;
    private int sn;
    private String userId;

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictText() {
        return dictText;
    }

    public void setDictText(String dictText) {
        this.dictText = dictText;
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

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
