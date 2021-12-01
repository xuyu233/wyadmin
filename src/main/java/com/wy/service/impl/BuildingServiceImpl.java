package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Building;
import com.wy.mapper.BuildingMapper;
import com.wy.service.BuildingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {


    @Override
    public Page<Building> getAllBuildings(Long page, Long limit) {
        return baseMapper.selectPage(new Page<>(page,limit),null);
    }

    @Override
    public int addBuilding(Building building) {
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Building>()
                .eq(Building::getName, building.getName()));
        if (count>0){
            return -1;
        }
        return baseMapper.insert(building);
    }

    @Override
    public int updateBuilding(Building building) {
        return baseMapper.updateById(building);
    }

    @Override
    public int delBuilding(Integer id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Building> findBuilding(Long page, Long limit, String name) {
        return baseMapper.selectPage(new Page<>(page,limit),
                new LambdaQueryWrapper<Building>()
                .like(Building::getName,name));
    }

    @Override
    public List<Building> getAllBuildings() {
        return baseMapper.selectList(null);
    }
}
