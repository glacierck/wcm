package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/8 0008 on 11:53
 * 作者:penny
 */
public class UpdateAppModel extends BaseModel{

    /**
     * content : app
     版本不息，优化不止
     优化了交互体验
     解决了BUG
     * id : 2
     * isLatest : 1
     * owner :
     * path : http://192.168.1.168:80/dis-shop-api/versionUpgrade/app-debug.apk
     * platformId : 0
     * type : 0
     * userId :
     * versionNo : 2.1.0
     */

    private String content;
    private String id;
    private int isLatest;
    private String owner;
    private String path;
    private int platformId;
    private int type;
    private String userId;
    private String versionNo;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(int isLatest) {
        this.isLatest = isLatest;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    @Override
    public String toString() {
        return "UpdateAppModel{" +
                "content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", isLatest=" + isLatest +
                ", owner='" + owner + '\'' +
                ", path='" + path + '\'' +
                ", platformId=" + platformId +
                ", type=" + type +
                ", userId='" + userId + '\'' +
                ", versionNo='" + versionNo + '\'' +
                '}';
    }
}
