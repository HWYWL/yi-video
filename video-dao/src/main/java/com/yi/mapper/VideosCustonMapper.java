package com.yi.mapper;

import com.yi.model.Videos;
import com.yi.utils.MyMapper;
import com.yi.vo.VideosVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关联用户和视频表查询
 * @author YI
 * @date 2018-6-13 14:46:41
 */
public interface VideosCustonMapper extends MyMapper<Videos> {

    /**
     * 查询所有视频
     * @param videoDesc 视频备注
     * @param userId    用户id
     * @return
     */
    List<VideosVo> queryAllVideos(@Param("videoDesc") String videoDesc, @Param("userId") String userId);

    /**
     * 对视频喜欢点赞
     * @param videoId 视频id
     */
    void addVideoLikeCount(String videoId);

    /**
     * 取消对视频喜欢点赞
     * @param videoId 视频id
     */
    void reduceVideoLikeCount(String videoId);
}