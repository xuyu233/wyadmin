package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Car;
import com.wy.mapper.CarMapper;
import com.wy.service.CarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    @Override
    public Page<Car> getAllCars(Long page, Long limit) {
        return baseMapper.selectPage(new Page<>(page,limit),null);
    }

    @Override
    public int addCar(Car car) {
        return baseMapper.insert(car);
    }

    @Override
    public int updateCar(Car car) {
        return baseMapper.updateById(car);
    }

    @Override
    public int delCar(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<Car> getAllFreeCars(int type) {
        return baseMapper.selectList(new LambdaQueryWrapper<Car>().eq(Car::getStatus,0).like(Car::getType,type));
    }

    @Override
    public Page<Car> findCar(Long page, Long limit, String name) {
        return baseMapper.selectPage(new Page<>(page,limit),new LambdaQueryWrapper<Car>().like(Car::getStatus,name));
    }

    @Override
    public int getCount() {
        return baseMapper.selectCount(null);
    }

    @Override
    public int getFreeCount() {
        return baseMapper.selectCount(new LambdaQueryWrapper<Car>().eq(Car::getStatus,0));
    }
}
