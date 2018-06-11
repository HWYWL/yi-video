package com.yi.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.yi.model.Users;
import com.yi.service.UserService;
import com.yi.utils.MessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录注册
 * @author YI
 * @date 2018-6-11 21:53:14
 */
@RestController
@Api(value = "注册登录接口", tags = "RegistLoginController")
public class RegistLoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param users
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value="用户注册", notes="用户注册的接口")
    public MessageResult regist(@RequestBody Users users){
        if (users != null || StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())){
            return MessageResult.errorMsg("用户名或者密码不能为空");
        }

        boolean isExist = userService.queryUsernameIsExist(users.getUsername());
        if (isExist){
            return MessageResult.errorMsg("用户名已存在");
        }else {
            users.setNickname(users.getUsername());
            users.setPassword(DigestUtil.md5Hex(users.getPassword()));

            userService.saveUser(users);
        }

        return MessageResult.ok();
    }
}
