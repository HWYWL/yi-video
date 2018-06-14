package com.yi.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.yi.model.Users;
import com.yi.service.UserService;
import com.yi.utils.MessageResult;
import com.yi.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录注册
 * @author YI
 * @date 2018-6-11 21:53:14
 */
@RestController
@Api(value = "用户接口登录注册", tags = "用户接口登录注册")
public class RegistLoginController extends BasicController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param users
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value="用户注册", notes="用户注册接口")
    public MessageResult regist(@RequestBody Users users){
        if (users == null || StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())){
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

        List<Users> usersList = userService.queryUsernameAndPassWord(users.getUsername(), users.getPassword());

        UsersVo usersVo = setUserRedisSessionToken(usersList.get(0));
        usersVo.setPassword("");

        return MessageResult.ok(usersVo);
    }

    /**
     * 用户登录
     * @param users
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value="用户登录", notes="用户登录接口")
    public MessageResult login(@RequestBody Users users){
        if (users == null || StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())){
            return MessageResult.errorMsg("用户名或者密码不能为空");
        }

        List<Users> usersList = userService.queryUsername(users.getUsername());
        if (usersList == null || usersList.size() == 0){
            return MessageResult.errorMsg("用户不存在!");
        }

        Users user = usersList.get(0);

        if (!user.getPassword().equals(DigestUtil.md5Hex(users.getPassword()))){
            return MessageResult.errorMsg("用户或密码不正确!");
        }

        UsersVo usersVo = setUserRedisSessionToken(user);
        usersVo.setPassword("");

        return MessageResult.ok(usersVo);
    }

    /**
     * 用户注销
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value="用户注销", notes="用户注销接口")
    @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="String", paramType="query")
    public MessageResult logout(String userId){
        redis.del(USER_REDIS_SESSION + ":" + userId);

        return MessageResult.ok();
    }

    /**
     * 把Token放入redis
     * @param users
     * @return
     */
    private UsersVo setUserRedisSessionToken(Users users){
        String token = SecureUtil.simpleUUID();

        redis.set(USER_REDIS_SESSION + ":" + users.getId(), token, REDIS_TIMEOUT);

        UsersVo usersVo = new UsersVo();

        BeanUtil.copyProperties(users, usersVo);
        usersVo.setUserToken(token);

        return usersVo;
    }
}
