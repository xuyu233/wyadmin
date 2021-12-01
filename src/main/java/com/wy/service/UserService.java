package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 */
public interface UserService extends IService<User> {

    Page<User> getUsers(Long page, Long limit);

    int addUser(User user);

    int updateUser(User user);

    int delUser(int id);

    Page<User> findUser(Long page, Long limit, String name);

    User loginByPassword(String phone, String password);

    int updatePass(Integer id, String newPsw);

    User getUserById(Integer id);

    int getCount();

    String sendCode(String phone);

    boolean sendEmail(User user);

    int findUserByPhone(String phone);
}
