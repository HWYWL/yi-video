package com.yi.service;

import com.yi.model.Users;
import com.yi.model.UsersExample;

import java.util.List;

/**
 * 用户操作
 * @author YI
 * @date 2018-6-11 22:03:55
 */
public interface UserService {

    /**
     * 查询用户是否存在
     * @param username 用户名
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 保存用户(用户注册)
     * @param user
     */
    void saveUser(Users user);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    List<Users> queryUsername(String username);

    /**
     * 通过同户名和密码查找用户
     * @param username  用户名
     * @param password  密码
     * @return
     */
    List<Users> queryUsernameAndPassWord(String username, String password);

    /**
     * 更新用户信息
     * @param users
     */
    void updateUserInfo(Users users);

    /**
     * 查询用户信息
     * @param userId
     */
    Users queryUserInfo(String userId);

    /**
     * 查询该视频 当前用户是否喜欢
     * @param loginUserId 登录用户id
     * @param videoId   视频id
     * @return
     */
    boolean isUserLikeVideo(String loginUserId, String videoId);

    /**
     * 增加用户和粉丝的关系
     * @param userId 用户id
     * @param fanId 粉丝id
     */
    void saveUserFanRelation(String userId, String fanId);

    /**
     * 删除用户和粉丝的关系
     * @param userId 用户id
     * @param fanId 粉丝id
     */
    void deleteUserFanRelation(String userId, String fanId);

    /**
     * 查询用户是否关注
     * @param userId 视频者拥有者id
     * @param fanid 粉丝id
     * @return
     */
    Boolean queryIsFollow(String userId, String fanid);
}
