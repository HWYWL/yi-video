package com.yi.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.yi.model.Users;
import com.yi.model.UsersReport;
import com.yi.service.UserService;
import com.yi.utils.MessageResult;
import com.yi.vo.PublisherVideo;
import com.yi.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="视频拥有者用户id", required=true,
                    dataType="String", paramType="query"),
            @ApiImplicitParam(name="fanId", value="粉丝id", required=true,
                    dataType="String", paramType="query")
    })
    @ApiOperation(value="查询用户信息", notes="查询用户信息接口")
    public MessageResult queryUserInfo(String userId, String fanId) {
        if (StringUtils.isBlank(userId)) {
            return MessageResult.errorMsg("用户id不能为空...");
        }

        Users users = userService.queryUserInfo(userId);

        UsersVo usersVo = new UsersVo();
        BeanUtil.copyProperties(users, usersVo);

        // 查看是否已关注
        usersVo.setFollow(userService.queryIsFollow(userId, fanId));

        return MessageResult.ok(usersVo);
    }

    @RequestMapping(value = "/queryPublisher", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="loginUserId", value="登录用户id", required=true,
                    dataType="String", paramType="query"),
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="query"),
            @ApiImplicitParam(name="publishUserId", value="视频发布者id", required=true,
                    dataType="String", paramType="query")
    })
    @ApiOperation(value="查询视频及用户信息", notes="查询视频及用户信息接口")
    public MessageResult queryPublisher(String loginUserId, String videoId, String publishUserId) {

        if (StringUtils.isBlank(publishUserId)) {
            return MessageResult.errorMsg("");
        }

        // 查询视频发布者的信息
        Users userInfo = userService.queryUserInfo(publishUserId);
        UsersVo publisher = new UsersVo();
        BeanUtils.copyProperties(userInfo, publisher);

        // 查询当前登录者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo publisherVideo = new PublisherVideo();
        publisherVideo.setPublisher(publisher);
        publisherVideo.setUserLikeVideo(userLikeVideo);

        return MessageResult.ok(publisherVideo);
    }


    @RequestMapping(value = "/beyourfans", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="query"),
            @ApiImplicitParam(name="fanId", value="粉丝id", required=true,
                    dataType="String", paramType="query")
    })
    @ApiOperation(value="关注成为粉丝", notes="关注成为粉丝接口")
    public MessageResult byyourfans(String userId, String fanId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return MessageResult.errorMsg("非法请求");
        }

        userService.saveUserFanRelation(userId, fanId);

        return MessageResult.ok();
    }

    @RequestMapping(value = "/dontbeyourfans", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="query"),
            @ApiImplicitParam(name="fanId", value="粉丝id", required=true,
                    dataType="String", paramType="query")
    })
    @ApiOperation(value="取消粉丝关注", notes="取消粉丝关注接口")
    public MessageResult dontbeyourfans(String userId, String fanId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return MessageResult.errorMsg("非法请求");
        }

        userService.deleteUserFanRelation(userId, fanId);

        return MessageResult.ok();
    }

    @RequestMapping(value = "/reportUser", method = RequestMethod.POST)
    @ApiOperation(value="举报用户", notes="举报用户接口")
    public MessageResult reportUser(@RequestBody UsersReport usersReport) {

        userService.reportUser(usersReport);

        return MessageResult.errorMsg("举报成功...");
    }
}
