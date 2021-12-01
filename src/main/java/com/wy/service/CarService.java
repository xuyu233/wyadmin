package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Car;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 */
public interface CarService extends IService<Car> {

    Page<Car> getAllCars(Long page, Long limit);

    int addCar(Car car);

    int updateCar(Car car);

    int delCar(int id);


    List<Car> getAllFreeCars(int type);

    Page<Car> findCar(Long page, Long limit, String name);

    int getCount();

    int getFreeCount();
}
