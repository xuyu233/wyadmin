package com.wy.service;

import com.wy.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {

    /**
     * 管理员登录
     * @param email
     * @param password
     * @return
     */
    Admin findAdmin(String email, String password);

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    int updatePass(Admin admin);

    /**
     * 查询是否存在此管理员邮箱
     * @param email
     * @return
     */
    int findAdminByEmail(String email);
}
