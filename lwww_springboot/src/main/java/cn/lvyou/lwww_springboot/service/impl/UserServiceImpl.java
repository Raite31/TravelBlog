package cn.lvyou.lwww_springboot.service.impl;

import cn.lvyou.lwww_springboot.entiy.User;
import cn.lvyou.lwww_springboot.mapper.UserMapper;
import cn.lvyou.lwww_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper; // 注入UserMapper
    @Override
    public User login(User user) {
        return userMapper.login(user); // 调用Mapper层代码
    }

    @Override
    public int updateUser(User user) {
        // 调用mapper修改用户信息
        return userMapper.update(user);
    }
}
