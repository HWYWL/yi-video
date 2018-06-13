package com.yi.service;

import com.yi.model.Videos;

/**
 * 视频操作
 * @author YI
 * @date 2018-6-11 22:03:55
 */
public interface VideoService {
    String save(Videos videos);

    void updateVideo(String videoId, String uploadPathDB);
}
