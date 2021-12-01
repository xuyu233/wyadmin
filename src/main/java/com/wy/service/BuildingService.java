package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Building;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 */
public interface BuildingService extends IService<Building> {

    Page<Building> getAllBuildings(Long page, Long limit);

    int addBuilding(Building building);

    int updateBuilding(Building building);

    int delBuilding(Integer id);

    Page<Building> findBuilding(Long page, Long limit, String name);

    List<Building> getAllBuildings();
}
