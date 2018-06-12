package com.yi.service;

import com.yi.model.Bgm;

import java.util.List;

/**
 * 用户操作
 * @author YI
 * @date 2018-6-11 22:03:55
 */
public interface BgmService {

    /**
     * 查询所有bgm列表
     * @return
     */
    List<Bgm> queryBgmList();
}
