package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.UserCar;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 */
public interface UserCarService extends IService<UserCar> {

    Page<UserCar> findCarRecordById(Long id, Long page, Long limit);

    int stopCarByUserId(int id);

    int findCar(Integer userId);

    void outCar(Integer userId);

    int fenpei(Integer userId, Integer carId);
}
