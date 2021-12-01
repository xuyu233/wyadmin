package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.common.CodecUtils;
import com.wy.pojo.Admin;
import com.wy.mapper.AdminMapper;
import com.wy.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    public Admin findAdmin(String email, String password) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<Admin>();
        queryWrapper.eq(Admin::getEmail,email);
        Admin admin = baseMapper.selectOne(queryWrapper);
        if(StringUtils.equals(CodecUtils.md5Hex(password,admin.getSalt()),admin.getPassword())){
            return admin;
        }
        return null;
    }

    public int updatePass(Admin admin) {
        return baseMapper.updateById(admin);
    }

    @Override
    public int findAdminByEmail(String email) {
        //根据邮箱返回是否存在此用户
        Admin admin = new Admin();
        admin.setEmail(email);
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getEmail,email);
        return baseMapper.selectCount(queryWrapper);

    }
}
