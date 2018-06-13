package com.yi.mapper;

import com.yi.model.Videos;
import com.yi.utils.MyMapper;
import com.yi.vo.VideosVo;

import java.util.List;

/**
 * 关联用户和视频表查询
 * @author YI
 * @date 2018-6-13 14:46:41
 */
public interface VideosCustonMapper extends MyMapper<Videos> {
    List<VideosVo> queryAllVideos();
}