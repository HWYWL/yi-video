package com.yi.controller;

import cn.hutool.core.io.FileUtil;
import com.yi.model.Users;
import com.yi.service.UserService;
import com.yi.utils.MessageResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "视频业务接口", tags = "视频业务接口")
@RequestMapping("/video")
public class VideoController extends BasicController {

    @Autowired
    private UserService userService;

    /**
     * 上传视频
     * @param userId 用户id
     * @param bgmId 背景音乐id
     * @param videoSeconds 背景音乐播放长度
     * @param videoWidth 视频宽度
     * @param videoHeight 视频高度
     * @param desc 视频描述
     * @param file 视频文件
     * @return
     */
    @ApiOperation(value="上传视频", notes="上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="bgmId", value="背景音乐id",
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoWidth", value="视频宽度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoHeight", value="视频高度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="desc", value="视频描述",
                    dataType="String", paramType="form")
    })
    @RequestMapping(value="/upload", headers="content-type=multipart/form-data", method = RequestMethod.POST)
    public MessageResult upload(String userId,
                                  String bgmId, double videoSeconds,
                                  int videoWidth, int videoHeight,
                                  String desc,
                                  @ApiParam(value="短视频", required=true) MultipartFile file) {
        if (StringUtils.isBlank(userId)) {
            return MessageResult.errorMsg("用户id不能为空...");
        }

        // 保存到数据库中的相对路径
        String uploadPath = "/YI_VIDEO";
        String uploadPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String finalVideoPath = uploadPath + uploadPathDB + "/" + fileName;
                FileUtil.touch(finalVideoPath);
                uploadPathDB += ("/" + fileName);

                File outFile = new File(finalVideoPath);
                fileOutputStream = new FileOutputStream(outFile);

                inputStream = file.getInputStream();
                IOUtils.copy(inputStream, fileOutputStream);
            }
        }catch (Exception e){
            return MessageResult.errorMsg("视频上传失败");
        }

        return MessageResult.ok();
    }
}
