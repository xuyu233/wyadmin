package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.UserMapper;
import com.wy.pojo.Repair;
import com.wy.mapper.RepairMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.pojo.Tousu;
import com.wy.service.RepairService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<Repair> getAllRepairs(Long page, Long limit) {
        Page<Repair> repairPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Repair>().orderByDesc(Repair::getTime));
        bindData(repairPage.getRecords());
        return repairPage;
    }

    private void bindData(List<Repair> records) {
        records.forEach(repair -> {
            repair.setUser(userMapper.selectById(repair.getUserId()));
        });
    }


    @Override
    public Page<Repair> getAllRepairsByUser(Long page, Long limit, Integer id) {
        return baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Repair>().eq(Repair::getUserId,id).orderByDesc(Repair::getTime));
    }

    @Override
    public int addRepair(Repair repair) {
        repair.setTime(new Date());
        repair.setStatus("0");
        return baseMapper.insert(repair);
    }

    @Override
    public int updateRepair(Repair repair) {
        return baseMapper.updateById(repair);
    }

    @Override
    public int delRepair(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Repair> findRepair(Long page, Long limit, String name) {
        Page<Repair> repairPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Repair>().eq(Repair::getStatus, name).orderByDesc(Repair::getTime));
        bindData(repairPage.getRecords());
        return repairPage;
    }

    @Override
    public int getCount() {
        return baseMapper.selectCount(null);
    }

    @Override
    public int getCountByUserId(Integer id) {
        return baseMapper.selectCount(new LambdaQueryWrapper<Repair>().eq(Repair::getUserId,id));
    }
}
