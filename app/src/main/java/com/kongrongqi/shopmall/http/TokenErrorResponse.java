package com.kongrongqi.shopmall.http;

import android.databinding.BaseObservable;
import android.databinding.PropertyChangeRegistry;

import java.io.Serializable;

/**
 * 创建日期：2017/6/7 0007 on 14:10
 * 作者:penny
 */
public class TokenErrorResponse extends BaseObservable implements Serializable {

    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    /**
     * timestamp : 1496815109323
     * status : 403
     * error : Forbidden
     * message : token错误
     * path : /v1/base/serves/getServiceList
     */

    private long timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "TokenErrorResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
