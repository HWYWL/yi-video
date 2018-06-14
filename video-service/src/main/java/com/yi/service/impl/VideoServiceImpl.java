package com.yi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yi.mapper.*;
import com.yi.model.SearchRecords;
import com.yi.model.UsersLikeVideos;
import com.yi.model.UsersLikeVideosExample;
import com.yi.model.Videos;
import com.yi.service.VideoService;
import com.yi.utils.PagedResult;
import com.yi.vo.VideosVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersMapper usersMapper;

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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 保存用户和视频的喜欢点赞关联关系表
        UsersLikeVideos ulv = new UsersLikeVideos();

        ulv.setId(sid.nextShort());
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);

        usersLikeVideosMapper.insert(ulv);

        // 视频喜欢数量增加
        videosCustonMapper.addVideoLikeCount(videoId);

        // 用户喜欢数量的累加
        usersMapper.addReceiveLikeCount(videoCreaterId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 删除用户和视频的喜欢点赞关联关系表
        UsersLikeVideosExample example = new UsersLikeVideosExample();
        UsersLikeVideosExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(userId);
        criteria.andVideoIdEqualTo(videoId);

        usersLikeVideosMapper.deleteByExample(example);

        // 视频喜欢数量累减
        videosCustonMapper.reduceVideoLikeCount(videoId);

        // 用户受喜欢数量的累减
        usersMapper.reduceReceiveLikeCount(videoCreaterId);
    }
}
