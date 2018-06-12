package com.yi.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.yi.model.Users;
import com.yi.service.UserService;
import com.yi.utils.MessageResult;
import com.yi.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 登录注册
 * @author YI
 * @date 2018-6-11 21:53:14
 */
@RestController
@Api(value = "用户接口", tags = "用户接口")
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    /**
     * 上传用户头像
     * @param userId    用户id
     * @param files 头像文件
     * @return
     */
    @RequestMapping(value = "/uploadFace", method = RequestMethod.POST)
    @ApiOperation(value="用户上传头像", notes="用户上传头像接口")
    public MessageResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) {
        if (StringUtils.isBlank(userId)) {
            return MessageResult.errorMsg("用户id不能为空...");
        }

        // 保存到数据库中的相对路径
        String uploadPath = "/YI_VIDEO";
        String uploadPathDB = "/" + userId + "/face";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                String finalFacePath = uploadPath + uploadPathDB + "/" + fileName;
                FileUtil.touch(finalFacePath);
                uploadPathDB += ("/" + fileName);

                File outFile = new File(finalFacePath);
                fileOutputStream = new FileOutputStream(outFile);

                inputStream = files[0].getInputStream();
                IOUtils.copy(inputStream, fileOutputStream);
            }
        }catch (Exception e){
            return MessageResult.errorMsg("头像上传失败");
        }

        //保存头像信息到数据库
        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);

        return MessageResult.ok(user);
    }

    /**
     * 查询用户接口
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/queryUserInfo", method = RequestMethod.POST)
    @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="String", paramType="query")
    @ApiOperation(value="查询用户信息", notes="查询用户信息接口")
    public MessageResult queryUserInfo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return MessageResult.errorMsg("用户id不能为空...");
        }

        Users users = userService.queryUserInfo(userId);

        UsersVo usersVo = new UsersVo();
        BeanUtil.copyProperties(users, usersVo);

        return MessageResult.ok(usersVo);
    }
}
