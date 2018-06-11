package com.yi.service;

import com.yi.model.Users;
import com.yi.model.UsersExample;

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
}
