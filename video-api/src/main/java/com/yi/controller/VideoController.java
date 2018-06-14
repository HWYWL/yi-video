package com.yi.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.yi.enums.VideoStatusEnum;
import com.yi.model.Bgm;
import com.yi.model.Comments;
import com.yi.model.Videos;
import com.yi.service.BgmService;
import com.yi.service.VideoService;
import com.yi.utils.FetchVideoCover;
import com.yi.utils.MergeVideoMp3;
import com.yi.utils.MessageResult;
import com.yi.utils.PagedResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

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
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

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
        // 封面
        String coverPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream;
        InputStream inputStream;

        // 视频文件上传路径
        String finalVideoPath = "";
        // bgm文件最终路径
        String finalbgmPath;
        // 合成视频路径
        String finalVideoOutPath = "";

        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                finalVideoPath = uploadPath + uploadPathDB + "/" + fileName;
                FileUtil.touch(finalVideoPath);

                File outFile = new File(finalVideoPath);
                fileOutputStream = new FileOutputStream(outFile);

                inputStream = file.getInputStream();
                IOUtils.copy(inputStream, fileOutputStream);
            }
        }catch (Exception e){
            return MessageResult.errorMsg("视频上传失败");
        }

        if (StringUtils.isNotBlank(bgmId)){
            Bgm bgm = bgmService.queryBgmById(bgmId);
            finalbgmPath = uploadPath + bgm.getPath();

            MergeVideoMp3 mergeVideoMp3 = new MergeVideoMp3(FFMPEG_EXE_FILE);
            FetchVideoCover fetchVideoCover = new FetchVideoCover(FFMPEG_EXE_FILE);

            try {
                String name = SecureUtil.simpleUUID();
                uploadPathDB += ("/" + name+ ".mp4");
                coverPathDB += ("/" + name+ ".jpg");
                finalVideoOutPath = uploadPath + uploadPathDB;
                // 视频合成
                mergeVideoMp3.convertor(finalVideoPath, finalbgmPath, videoSeconds, finalVideoOutPath);
                // 封面截图
                fetchVideoCover.getCover(finalVideoPath, uploadPath + coverPathDB);
            } catch (Exception e) {
                e.printStackTrace();
                return MessageResult.errorMsg("视频合成失败!");
            }
        }

        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float)videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        String videoId = videoService.save(video);

        return MessageResult.ok(videoId);
    }

    /**
     * 上传视频封面 应为微信的bug，此功能作废
     * @param userId
     * @param videoId
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation(value="上传封面", notes="上传封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="form")
    })
    @RequestMapping(value="/uploadCover", headers="content-type=multipart/form-data", method = RequestMethod.POST)
    @Deprecated
    public MessageResult uploadCover(String userId, String videoId,
                                       @ApiParam(value="视频封面", required=true) MultipartFile file) throws Exception {
        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
            return MessageResult.errorMsg("视频主键id和用户id不能为空...");
        }

        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream;
        // 文件上传的最终保存路径
        String finalCoverPath;
        try {
            if (file != null) {

                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {

                    finalCoverPath = uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);

                    FileUtil.touch(finalCoverPath);

                    File outFile = new File(finalCoverPath);
                    fileOutputStream = new FileOutputStream(outFile);

                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }

            } else {
                return MessageResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MessageResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        videoService.updateVideo(videoId, uploadPathDB);

        return MessageResult.ok();
    }

    /**
     * 查询视频分页接口
     * @param video
     * @param isSaveRecord 1 - 需要保存 0 - 不需要保存 ，或者为空的时候
     * @param page
     * @return
     */
    @ApiImplicitParam(name="page", value="当前页数", required=true, dataType="Integer", paramType="query")
    @ApiOperation(value="视频分页", notes="查询视频分页接口")
    @RequestMapping(value="/showAll", method = RequestMethod.POST)
    public MessageResult showAll(@RequestBody Videos video, Integer isSaveRecord, Integer page) {
        page = page == null ? 1 : page;

        PagedResult videos = videoService.getAllVideos(video, isSaveRecord, page, PAGE_SIZE);

        return MessageResult.ok(videos);
    }

    /**
     * 热搜词
     * @return
     */
    @ApiOperation(value="热搜", notes="查询热搜接口")
    @RequestMapping(value="/hot", method = RequestMethod.POST)
    public MessageResult hot() {
        List<String> hotWords = videoService.getHotWords();
        return MessageResult.ok(hotWords);
    }

    /**
     * 点赞
     * @param userId 点赞人用户id
     * @param videoId 视频id
     * @param videoCreaterId 视频拥有者用户id
     * @return
     */
    @ApiOperation(value="点赞", notes="点赞接口")
    @RequestMapping(value="/userLike", method = RequestMethod.POST)
    public MessageResult userLike(String userId, String videoId, String videoCreaterId) {
        videoService.userLikeVideo(userId, videoId, videoCreaterId);
        return MessageResult.ok();
    }

    /**
     * 取消点赞
     * @param userId 取消点赞人用户id
     * @param videoId 视频id
     * @param videoCreaterId 视频拥有者用户id
     * @return
     */
    @ApiOperation(value="取消点赞", notes="取消点赞接口")
    @RequestMapping(value="/unUserLike", method = RequestMethod.POST)
    public MessageResult unUserLike(String userId, String videoId, String videoCreaterId) {
        videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return MessageResult.ok();
    }

    /**
     * 我关注的人发的视频
     * @param userId
     * @param page
     * @return
     */
    @ApiOperation(value="我关注的人发的视频", notes="我关注的人发的视频接口")
    @RequestMapping(value="/showMyFollow", method = RequestMethod.POST)
    public MessageResult showMyFollow(String userId, Integer page) {

        if (StringUtils.isBlank(userId)) {
            return MessageResult.ok();
        }

        page = page == null ? 1 : page;

        PagedResult videosList = videoService.queryMyFollowVideos(userId, page, PAGE_SIZE);

        return MessageResult.ok(videosList);
    }

    /**
     * 点赞过的视频列表
     * @param userId 用户id
     * @param page 当前页
     * @return
     */
    @ApiOperation(value="我点赞过视频", notes="我点赞过视频接口")
    @RequestMapping(value="/showMyLike", method = RequestMethod.POST)
    public MessageResult showMyLike(String userId, Integer page){

        if (StringUtils.isBlank(userId)) {
            return MessageResult.ok();
        }

        page = page == null ? 1 : page;

        PagedResult videosList = videoService.queryMyLikeVideos(userId, page, PAGE_SIZE);

        return MessageResult.ok(videosList);
    }

    /**
     * 留言保存
     * @param comment 留言
     * @param fatherCommentId 父评论id
     * @param toUserId 回复评论人id
     * @return
     */
    @RequestMapping(value = "/saveComment", method = RequestMethod.POST)
    @ApiOperation(value="留言保存", notes="留言保存接口")
    public MessageResult saveComment(@RequestBody Comments comment, String fatherCommentId, String toUserId) {
        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);

        videoService.saveComment(comment);
        return MessageResult.ok();
    }

    @RequestMapping(value = "/getVideoComments", method = RequestMethod.POST)
    @ApiOperation(value="获取留言", notes="获取留言接口")
    public MessageResult getVideoComments(String videoId, Integer page) {
        if (StringUtils.isBlank(videoId)) {
            return MessageResult.ok();
        }

        page = page == null ? 1 : page;
        PagedResult list = videoService.getAllComments(videoId, page, PAGE_SIZE);

        return MessageResult.ok(list);
    }
}
