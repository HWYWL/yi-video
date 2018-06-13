package com.yi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yi.mapper.SearchRecordsMapper;
import com.yi.mapper.VideosCustonMapper;
import com.yi.mapper.VideosMapper;
import com.yi.model.SearchRecords;
import com.yi.model.Videos;
import com.yi.service.VideoService;
import com.yi.utils.PagedResult;
import com.yi.vo.VideosVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private VideosCustonMapper videosCustonMapper;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

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

    @Override
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
        // 保存热搜词
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        }

        // 分页查询 视频描述的视频信息
        PageHelper.startPage(page, pageSize);
        List<VideosVo> allVideos = videosCustonMapper.queryAllVideos(desc, userId);

        // 数据封装 返回
        PageInfo<VideosVo> voPageInfo = new PageInfo<>(allVideos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(voPageInfo.getPages());
        pagedResult.setRows(allVideos);
        pagedResult.setRecords(voPageInfo.getTotal());

        return pagedResult;
    }

    @Override
    public List<String> getHotWords() {
        return searchRecordsMapper.getHotWords();
    }
}
