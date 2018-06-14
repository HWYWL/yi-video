package com.yi.mapper;

import com.yi.model.Users;
import com.yi.model.UsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    long countByExample(UsersExample example);

    int deleteByExample(UsersExample example);

    int deleteByPrimaryKey(String id);

    int insert(Users record);

    int insertSelective(Users record);

    List<Users> selectByExample(UsersExample example);

    Users selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    /**
     * 用户受喜欢数累加
     * @param userId 用户id
     */
    void addReceiveLikeCount(String userId);

    /**
     * 用户受喜欢数累减
     * @param userId 用户id
     */
    void reduceReceiveLikeCount(String userId);

    /**
     * 增加粉丝数
     * @param userId 用户id
     */
    void addFansCount(String userId);

    /**
     * 增加关注数
     * @param userId 用户id
     */
    void addFollersCount(String userId);

    /**
     * 减少粉丝数
     * @param userId 用户id
     */
    void reduceFansCount(String userId);

    /**
     * 减少关注数
     * @param userId 用户id
     */
    void reduceFollersCount(String userId);
}