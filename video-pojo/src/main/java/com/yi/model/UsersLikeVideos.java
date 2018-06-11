package com.yi.model;

import java.io.Serializable;

/**
 * @author 
 */
public class UsersLikeVideos implements Serializable {
    private String id;

    /**
     * 用户
     */
    private String userId;

    /**
     * 视频
     */
    private String videoId;

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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}