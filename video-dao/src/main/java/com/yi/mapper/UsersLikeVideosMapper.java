package com.yi.mapper;

import com.yi.model.UsersLikeVideos;
import com.yi.model.UsersLikeVideosExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersLikeVideosMapper {
    long countByExample(UsersLikeVideosExample example);

    int deleteByExample(UsersLikeVideosExample example);

    int deleteByPrimaryKey(String id);

    int insert(UsersLikeVideos record);

    int insertSelective(UsersLikeVideos record);

    List<UsersLikeVideos> selectByExample(UsersLikeVideosExample example);

    UsersLikeVideos selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UsersLikeVideos record, @Param("example") UsersLikeVideosExample example);

    int updateByExample(@Param("record") UsersLikeVideos record, @Param("example") UsersLikeVideosExample example);

    int updateByPrimaryKeySelective(UsersLikeVideos record);

    int updateByPrimaryKey(UsersLikeVideos record);
}