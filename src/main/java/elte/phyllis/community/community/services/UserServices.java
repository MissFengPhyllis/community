package elte.phyllis.community.community.services;

import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //插入数据库的过程就相当于写入session了，用数据库实物的存储代替了session的写入
            userMapper.insert(user);
            //插入
        }else{
            //更新
            dbUser.setGmtCreate(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
