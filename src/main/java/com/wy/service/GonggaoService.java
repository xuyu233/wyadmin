package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Gonggao;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 */
public interface GonggaoService extends IService<Gonggao> {

    Page<Gonggao> getAllGonggaos(Long page, Long limit);

    List<Gonggao> getAllShowGonggaos();

    int addGonggao(Gonggao gonggao);

    int updateGonggao(Gonggao gonggao);

    int delGonggao(int id);

    Page<Gonggao> findGonggao(Long page, Long limit, String name);

    Gonggao getGonggao();

    int getCount();
}
