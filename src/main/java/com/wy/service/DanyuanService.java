package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Danyuan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 */
public interface DanyuanService extends IService<Danyuan> {

    Page<Danyuan> getAllDanyuans(Long page, Long limit);

    int addDanyuan(Danyuan danyuan);

    int updateDanyuan(Danyuan danyuan);

    int delDanyuan(int id);

    Page<Danyuan> findDanyuan(Long page, Long limit, String name);

    List<Danyuan> getAllDanyuans();
}
