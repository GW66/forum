package com.gw.forum.forum.service;

import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void userUpdate(User user) {
//        判断所要的条件
        String account_id=user.getAccountId();
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(account_id);
        List<User> users = userMapper.selectByExample(userExample);
//        判断用户是否存在
        if (users.size()==0){
//            创建用户
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(users.get(0).getGmtCreate());
            userMapper.insert(user);
        }else {
//            更新用户
            user.setGmtCreate(users.get(0).getGmtCreate());
            user.setGmtModified(System.currentTimeMillis());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(user,example);
        }
    }
}
