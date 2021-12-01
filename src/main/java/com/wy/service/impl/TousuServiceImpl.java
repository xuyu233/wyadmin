package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.UserMapper;
import com.wy.pojo.Tousu;
import com.wy.mapper.TousuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.service.TousuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 */
@Service
public class  TousuServiceImpl extends ServiceImpl<TousuMapper, Tousu> implements TousuService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<Tousu> getAllTousus(Long page, Long limit) {
        Page<Tousu> tousuPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Tousu>().orderByDesc(Tousu::getTime));
        bindData(tousuPage.getRecords());
        return tousuPage;
    }

    private void bindData(List<Tousu> records) {
        records.forEach(tousu -> {
            tousu.setUser(userMapper.selectById(tousu.getUserId()));
        });
    }

    @Override
    public Page<Tousu> getAllToususByUser(Long page, Long limit, Integer id) {
        return baseMapper.selectPage(new Page<>(page,limit),new LambdaQueryWrapper<Tousu>().eq(Tousu::getUserId,id).orderByDesc(Tousu::getTime));
    }

    @Override
    public int addTousu(Tousu tousu) {
        tousu.setTime(new Date());
        return baseMapper.insert(tousu);
    }

    @Override
    public int updateTousu(Tousu tousu) {
        return baseMapper.updateById(tousu);
    }

    @Override
    public int delTousu(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Tousu> findTousu(Long page, Long limit, String name) {
        Page<Tousu> tousuPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Tousu>().eq(Tousu::getStatus, name).orderByDesc(Tousu::getTime));
        bindData(tousuPage.getRecords());
        return tousuPage;
    }

    @Override
    public int getCount() {
        return baseMapper.selectCount(null);
    }

    @Override
    public int getCountByUserId(Integer id) {
        return baseMapper.selectCount(new LambdaQueryWrapper<Tousu>().eq(Tousu::getUserId,id));
    }
}
