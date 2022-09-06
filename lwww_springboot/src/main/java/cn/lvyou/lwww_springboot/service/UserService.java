package cn.lvyou.lwww_springboot.service;

import cn.lvyou.lwww_springboot.entiy.User;

public interface UserService {
    /**
     * 根据用户名和密码登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(User user);
}
