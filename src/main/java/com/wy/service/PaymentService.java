package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Payment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 */
public interface PaymentService extends IService<Payment> {

    Page<Payment> getAllPayments(Long page, Long limit);

    int addPayment(Payment payment);

    int updatePayment(Payment payment);

    int delPayment(int id);

    Page<Payment> findPayment(Long page, Long limit, String name);

    List<Payment> getAllPayments();
}
