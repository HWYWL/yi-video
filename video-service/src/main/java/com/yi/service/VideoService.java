package com.yi.service;

import com.yi.model.Comments;
import com.yi.model.Videos;
import com.yi.utils.PagedResult;

import java.util.List;

/**
 * 视频操作
 * @author YI
 * @date 2018-6-11 22:03:55
 */
public interface VideoService {
    /**
     * 保存视频信息
     * @param videos
     * @return
     */
    String save(Videos videos);

    /**
     * 更新数据库视频信息
     * @param videoId
     * @param uploadPathDB
     */
    void updateVideo(String videoId, String uploadPathDB);

    /**
     * 分页查询视频列表
     * @param page      第几页
     * @param pageSize  页大小
     * @return
     */
    PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

    /**
     * 获取热搜词
     * @return
     */
    List<String> getHotWords();

    /**
     * 点赞
     * @param userId 点赞人用户id
     * @param videoId 视频id
     * @param videoCreaterId 视频拥有者用户id
     * @return
     */
     void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * 取消点赞
     * @param userId 取消点赞人用户id
     * @param videoId 视频id
     * @param videoCreaterId 视频拥有者用户id
     * @return
     */
    void userUnLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * 查询我关注的人的视频列表
     * @param userId    用户id
     * @param page      第几页
     * @param pageSize  页大小
     * @return
     */
    PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);

    /**
     * 查询我喜欢的视频列表
     * @param userId    用户id
     * @param page      第几页
     * @param pageSize  页大小
     * @return
     */
    PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    /**
     * 保存用户留言
     * @param comment
     */
    void saveComment(Comments comment);

    /**
     * 留言分页
     * @param videoId   视频id
     * @param page      第几页
     * @param pageSize  页大小
     * @return
     */
    PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
}
