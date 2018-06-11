package com.yi.model;

import java.io.Serializable;

/**
 * @author 
 */
public class UsersFans implements Serializable {
    private String id;

    /**
     * 用户
     */
    private String userId;

    /**
     * 粉丝
     */
    private String fanId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFanId() {
        return fanId;
    }

    public void setFanId(String fanId) {
        this.fanId = fanId;
    }
}