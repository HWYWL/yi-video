package com.yi.controller;

import com.yi.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用redisTemplate的操作实现类
 * @author YI
 * @date 2018-6-11 17:53:38
 */
@RestController
public class BasicController {
    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user_redis_session";
}
