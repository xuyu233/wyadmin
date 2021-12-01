package com.wy.mapper;

import com.wy.pojo.Car;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 */
public interface CarMapper extends BaseMapper<Car> {

    @Select("SELECT car.id,car.name,car.type,car.`status` FROM car,user,user_car WHERE car.id = car_id and user_id = user.id and user_car.outTime is NULL and user_id = #{userId}")
    List<Car> selectCarByUser(Integer userId);

}
