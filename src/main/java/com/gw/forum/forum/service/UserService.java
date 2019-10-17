package com.gw.forum.forum.service;

import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void userUpdate(User user) {
//        判断所要的条件
        String account_id=user.getAccountid();
        User exist=userMapper.findbyaccountid(account_id);
//        判断用户是否存在
        if (exist==null){
//            创建用户
            user.setGmtmodified(user.getGmtcreate());
            userMapper.insert(user);
        }else {
//            更新用户
            user.setGmtmodified(System.currentTimeMillis());
            userMapper.update(user);
        }
    }
}
