package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.UserPayment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 */
public interface UserPaymentService extends IService<UserPayment> {

    Page<UserPayment> getAllPaymentDetails(Long page, Long limit, int user_id);

    Page<UserPayment> getAllPaymentDetails(Long page, Long limit, String name);

    Page<UserPayment> getAllPaymentDetails(Long page, Long limit);

    int fenpei(Integer userId, Integer paymentId, String value);

    int jiaofei(int id);

    int getCount();

    int getFreeCount();

    int getCountByUserId(Integer id);
}
