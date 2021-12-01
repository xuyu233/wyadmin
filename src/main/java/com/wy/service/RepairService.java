package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Repair;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 */
public interface RepairService extends IService<Repair> {

    Page<Repair> getAllRepairs(Long page, Long limit);

    Page<Repair> getAllRepairsByUser(Long page, Long limit, Integer id);

    int addRepair(Repair repair);

    int updateRepair(Repair repair);

    int delRepair(int id);

    Page<Repair> findRepair(Long page, Long limit, String name);

    int getCount();

    int getCountByUserId(Integer id);
}
