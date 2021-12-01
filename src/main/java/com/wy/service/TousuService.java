package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Tousu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 */
public interface TousuService extends IService<Tousu> {

    Page<Tousu> getAllTousus(Long page, Long limit);

    Page<Tousu> getAllToususByUser(Long page, Long limit, Integer id);

    int addTousu(Tousu tousu);

    int updateTousu(Tousu tousu);

    int delTousu(int id);

    Page<Tousu> findTousu(Long page, Long limit, String name);

    int getCount();

    int getCountByUserId(Integer id);
}
