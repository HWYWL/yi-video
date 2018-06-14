package com.yi.service.impl;

import com.yi.mapper.UsersFansMapper;
import com.yi.mapper.UsersLikeVideosMapper;
import com.yi.mapper.UsersMapper;
import com.yi.mapper.UsersReportMapper;
import com.yi.model.*;
import com.yi.model.UsersExample.Criteria;
import com.yi.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户操作实现类
 * @author YI
 * @date 2018-6-11 22:18:21
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    UsersFansMapper usersFansMapper;

    @Autowired
    UsersReportMapper usersReportMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        UsersExample example = new UsersExample();
        Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(username);

        long count = usersMapper.countByExample(example);

        return count == 0 ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveUser(Users user) {
        user.setId(sid.nextShort());
        usersMapper.insert(user);
    }

    @Override
    public List<Users> queryUsername(String username) {
        UsersExample example = new UsersExample();
        Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(username);

        return usersMapper.selectByExample(example);
    }

    @Override
    public List<Users> queryUsernameAndPassWord(String username, String password) {
        UsersExample example = new UsersExample();
        Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);

        return usersMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(Users users) {
        usersMapper.updateByPrimaryKeySelective(users);
    }

    @Override
    public Users queryUserInfo(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean isUserLikeVideo(String loginUserId, String videoId) {
        UsersLikeVideosExample example = new UsersLikeVideosExample();
        UsersLikeVideosExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(loginUserId);
        criteria.andVideoIdEqualTo(videoId);

        List<UsersLikeVideos> likeVideosList = usersLikeVideosMapper.selectByExample(example);

        return likeVideosList != null && likeVideosList.size() > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveUserFanRelation(String userId, String fanId) {
        UsersFans usersFans = new UsersFans();

        usersFans.setId(sid.nextShort());
        usersFans.setUserId(userId);
        usersFans.setFanId(fanId);

        // 添加粉丝信息到数据库
        usersFansMapper.insert(usersFans);
        // 增加粉丝数
        usersMapper.addFansCount(userId);
        // 增加关注数
        usersMapper.addFollersCount(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteUserFanRelation(String userId, String fanId) {
        UsersFansExample example = new UsersFansExample();
        UsersFansExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(userId);
        criteria.andFanIdEqualTo(fanId);

        // 删除数据库中关联的粉丝信息
        usersFansMapper.deleteByExample(example);

        // 减少粉丝数
        usersMapper.reduceFansCount(userId);
        // 减少关注数
        usersMapper.reduceFollersCount(userId);
    }

    @Override
    public Boolean queryIsFollow(String userId, String fanid) {
        UsersFansExample example = new UsersFansExample();
        UsersFansExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(userId);
        criteria.andFanIdEqualTo(fanid);

        List<UsersFans> fansList = usersFansMapper.selectByExample(example);

        return fansList != null && fansList.size() > 0;
    }

    @Override
    public void reportUser(UsersReport usersReport) {
        usersReport.setId(sid.nextShort());
        usersReport.setCreateDate(new Date());

        usersReportMapper.insert(usersReport);
    }
}
