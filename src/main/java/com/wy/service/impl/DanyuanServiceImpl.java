package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.BuildingMapper;
import com.wy.pojo.Danyuan;
import com.wy.mapper.DanyuanMapper;
import com.wy.service.DanyuanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 */
@Service
public class DanyuanServiceImpl extends ServiceImpl<DanyuanMapper, Danyuan> implements DanyuanService {

    @Resource
    private BuildingMapper buildingMapper;

    private void bindBuilding(List<Danyuan> records) {
        records.forEach(d->{
            d.setBuilding(buildingMapper.selectById(d.getBuildingId()));
        });
    }

    @Override
    public Page<Danyuan> getAllDanyuans(Long page, Long limit) {
        Page<Danyuan> danyuanPage = baseMapper.selectPage(new Page<>(page,limit),null);
        bindBuilding(danyuanPage.getRecords());
        return danyuanPage;
    }

    @Override
    public int addDanyuan(Danyuan danyuan) {
        return baseMapper.insert(danyuan);
    }

    @Override
    public int updateDanyuan(Danyuan danyuan) {
        return baseMapper.updateById(danyuan);
    }

    @Override
    public int delDanyuan(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Danyuan> findDanyuan(Long page, Long limit, String name) {
        Page<Danyuan> danyuanPage = baseMapper.selectPage(new Page<>(page,limit),new LambdaQueryWrapper<Danyuan>().like(Danyuan::getName,name));
        bindBuilding(danyuanPage.getRecords());
        return danyuanPage;
    }

    @Override
    public List<Danyuan> getAllDanyuans() {
        List<Danyuan> lists = baseMapper.selectList(null);
        bindBuilding(lists);
        return lists;
    }
}
