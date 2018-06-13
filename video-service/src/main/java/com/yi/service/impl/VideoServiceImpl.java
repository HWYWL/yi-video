package com.yi.service.impl;

import com.yi.mapper.VideosMapper;
import com.yi.model.Videos;
import com.yi.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 视频实现类
 * @author YI
 * @date 2018-6-13 10:42:57
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String save(Videos videos) {
        String id = sid.nextShort();
        videos.setId(id);
        videosMapper.insertSelective(videos);

        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateVideo(String videoId, String uploadPathDB) {
        Videos videos = new Videos();
        videos.setId(videoId);
        videos.setCoverPath(uploadPathDB);

        videosMapper.updateByPrimaryKeySelective(videos);
    }
}
