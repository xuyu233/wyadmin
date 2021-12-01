package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Payment;
import com.wy.mapper.PaymentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Override
    public Page<Payment> getAllPayments(Long page, Long limit) {
        return baseMapper.selectPage(new Page<>(page,limit),null);
    }

    @Override
    public int addPayment(Payment payment) {
        return baseMapper.insert(payment);
    }

    @Override
    public int updatePayment(Payment payment) {
        return baseMapper.updateById(payment);
    }

    @Override
    public int delPayment(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Payment> findPayment(Long page, Long limit, String name) {
        return baseMapper.selectPage(new Page<>(page,limit),new LambdaQueryWrapper<Payment>().like(Payment::getName,name));
    }

    @Override
    public List<Payment> getAllPayments() {
        return baseMapper.selectList(null);
    }
}
