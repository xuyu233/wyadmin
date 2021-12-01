package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.CarMapper;
import com.wy.mapper.UserMapper;
import com.wy.pojo.Car;
import com.wy.pojo.UserCar;
import com.wy.mapper.UserCarMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.service.UserCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 */
@Service
public class UserCarServiceImpl extends ServiceImpl<UserCarMapper, UserCar> implements UserCarService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private CarMapper carMapper;

    @Override
    public Page<UserCar> findCarRecordById(Long id, Long page, Long limit) {
        Page<UserCar> userCarPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<UserCar>().eq(UserCar::getCarId, id));
        bindData(userCarPage.getRecords());
        return userCarPage;
    }

    private void bindData(List<UserCar> records) {
        records.forEach(userCar -> {
            userCar.setUser(userMapper.selectById(userCar.getUserId()));
            userCar.setCar(carMapper.selectById(userCar.getCarId()));
        });
    }

    @Override
    public int stopCarByUserId(int id) {
        List<UserCar> userCars = baseMapper.selectList(new LambdaQueryWrapper<UserCar>().eq(UserCar::getUserId, id).isNull(UserCar::getOuttime));
        if (userCars.isEmpty()){
            return 1;
        }
        UserCar userCar = userCars.get(0);
        Car car = new Car();
        car.setId(userCar.getCarId());
        car.setStatus(0);
        carMapper.updateById(car);
        return baseMapper.update(null,new LambdaUpdateWrapper<UserCar>().eq(UserCar::getUserId,id).isNull(UserCar::getOuttime).set(UserCar::getOuttime,new Date()));
    }

    @Override
    public int findCar(Integer userId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<UserCar>().eq(UserCar::getUserId, userId).isNull(UserCar::getOuttime));
    }

    @Override
    public void outCar(Integer userId) {
        List<UserCar> userCars = baseMapper.selectList(new LambdaQueryWrapper<UserCar>().eq(UserCar::getUserId, userId).isNull(UserCar::getOuttime));
        UserCar userCar = userCars.get(0);
        Car car = new Car();
        car.setId(userCar.getCarId());
        car.setStatus(0);
        carMapper.updateById(car);
        baseMapper.update(null,new LambdaUpdateWrapper<UserCar>().eq(UserCar::getUserId,userId).isNull(UserCar::getOuttime).set(UserCar::getOuttime,new Date()));
    }

    @Override
    public int fenpei(Integer userId, Integer carId) {
        Car car = new Car();
        car.setId(carId);
        car.setStatus(1);
        carMapper.updateById(car);

        UserCar userCar = new UserCar();
        userCar.setUserId(userId);
        userCar.setCarId(carId);
        userCar.setIntime(new Date());
        return baseMapper.insert(userCar);
    }
}
