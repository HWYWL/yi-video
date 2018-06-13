package com.yi.controller;

import com.yi.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用redisTemplate的操作实现类
 * @author YI
 * @date 2018-6-11 17:53:38
 */
@Configuration
@PropertySource("classpath:config/config.properties")
@RestController
public class BasicController {
    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user_redis_session";

    public static String FFMPEG_EXE_FILE;

    @Value("${ffmpeg.path}")
    public void setFfmpegExeFile(String ffmpegExeFile) {
        FFMPEG_EXE_FILE = ffmpegExeFile;
    }
}
