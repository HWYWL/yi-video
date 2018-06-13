package com.yi.service;

import com.yi.model.Bgm;

import java.util.List;

/**
 * BGM操作
 * @author YI
 * @date 2018-6-11 22:03:55
 */
public interface BgmService {

    /**
     * 查询所有bgm列表
     * @return
     */
    List<Bgm> queryBgmList();

    /**
     * 根据id查询bgm
     * @param bgmId
     * @return
     */
    Bgm queryBgmById(String bgmId);
}
