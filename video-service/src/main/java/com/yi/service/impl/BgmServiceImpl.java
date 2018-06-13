package com.yi.service.impl;

import com.yi.mapper.BgmMapper;
import com.yi.mapper.UsersMapper;
import com.yi.model.Bgm;
import com.yi.model.Users;
import com.yi.model.UsersExample;
import com.yi.model.UsersExample.Criteria;
import com.yi.service.BgmService;
import com.yi.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * bgm背景音乐实现类
 * @author YI
 * @date 2018-6-11 22:18:21
 */
@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    @Override
    public Bgm queryBgmById(String bgmId) {
        return bgmMapper.selectByPrimaryKey(bgmId);
    }
}
