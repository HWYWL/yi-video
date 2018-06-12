package com.yi.controller;

import com.yi.service.BgmService;
import com.yi.utils.MessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * BGM音乐
 * @author YI
 * @date 2018-6-11 17:53:38
 */
@Api(value="背景音乐业务接口", tags= {"背景音乐业务"})
@RestController
@RequestMapping("/bgm")
public class BgmController {
    @Autowired
    private BgmService bgmService;

    /**
     * 获取所有bgm
     * @return
     */
    @ApiOperation(value="获取背景音乐列表", notes="获取背景音乐列表接口")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public MessageResult list(){
        return MessageResult.ok(bgmService.queryBgmList());
    }
}
