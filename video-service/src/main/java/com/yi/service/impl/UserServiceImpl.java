package com.yi.service.impl;

import com.yi.mapper.UsersMapper;
import com.yi.model.Users;
import com.yi.model.UsersExample;
import com.yi.model.UsersExample.Criteria;
import com.yi.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
