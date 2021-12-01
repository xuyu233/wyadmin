package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.PaymentMapper;
import com.wy.mapper.UserMapper;
import com.wy.pojo.Repair;
import com.wy.pojo.Room;
import com.wy.pojo.UserPayment;
import com.wy.mapper.UserPaymentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.pojo.UserRoom;
import com.wy.service.UserPaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 */
@Service
public class UserPaymentServiceImpl extends ServiceImpl<UserPaymentMapper, UserPayment> implements UserPaymentService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PaymentMapper paymentMapper;

    @Override
    public Page<UserPayment> getAllPaymentDetails(Long page, Long limit, int user_id) {
        Page<UserPayment> userPaymentPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<UserPayment>().like(UserPayment::getUserId,user_id).orderByDesc(UserPayment::getTime));
        bindData(userPaymentPage.getRecords());
        return userPaymentPage;
    }

    @Override
    public Page<UserPayment> getAllPaymentDetails(Long page, Long limit, String name) {
        Page<UserPayment> userPaymentPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<UserPayment>().like(UserPayment::getStatus,name).orderByDesc(UserPayment::getTime));
        bindData(userPaymentPage.getRecords());
        return userPaymentPage;
    }

    @Override
    public Page<UserPayment> getAllPaymentDetails(Long page, Long limit) {
        Page<UserPayment> userPaymentPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<UserPayment>().orderByDesc(UserPayment::getTime));
        bindData(userPaymentPage.getRecords());
        return userPaymentPage;
    }

    private void bindData(List<UserPayment> records) {
        records.forEach(userPayment -> {
            userPayment.setUser(userMapper.selectById(userPayment.getUserId()));
            userPayment.setPayment(paymentMapper.selectById(userPayment.getPaymentId()));
        });
    }

    @Override
    public int fenpei(Integer userId, Integer paymentId, String value) {
        UserPayment userPayment = new UserPayment();
        userPayment.setUserId(userId);
        userPayment.setPaymentId(paymentId);
        userPayment.setValue(value);
        userPayment.setTime(new Date());
        userPayment.setStatus(0);
        return baseMapper.insert(userPayment);
    }

    @Override
    public int jiaofei(int id) {
        UserPayment userPayment = new UserPayment();
        userPayment.setStatus(1);
        userPayment.setId(id);
        return baseMapper.updateById(userPayment);
    }

    @Override
    public int getCount() {
        return baseMapper.selectCount(null);
    }

    @Override
    public int getFreeCount() {
        return baseMapper.selectCount(new LambdaQueryWrapper<UserPayment>().eq(UserPayment::getStatus,0));
    }

    @Override
    public int getCountByUserId(Integer id) {
        return baseMapper.selectCount(new LambdaQueryWrapper<UserPayment>().eq(UserPayment::getUserId,id));
    }
}
