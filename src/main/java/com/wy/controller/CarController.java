package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Car;
import com.wy.pojo.ResBody;
import com.wy.service.CarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 车位管理
 */
@RestController
public class CarController {
    @Resource
    private CarService carService;

    /**
     * 查询所有车位
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/api/getAllCars")
    public ResBody getAllCars(@RequestParam Long page,
                                   @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Car> pages= carService.getAllCars(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    /**
     * 添加车位
     * @param car
     * @return
     */
    @PostMapping("/api/addCar")
    public ResBody addBuilding(@RequestBody Car car) {
        ResBody resBody = new ResBody();
        int i = carService.addCar(car);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    /**
     * 修改车位
     * @param car
     * @return
     */
    @PostMapping("/api/updateCar")
    public ResBody updateCar(@RequestBody Car car) {
        ResBody resBody = new ResBody();
        int i = carService.updateCar(car);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    /**
     * 删除车位
     * @param id
     * @return
     */
    @GetMapping("/api/delCar")
    public ResBody delCar(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = carService.delCar(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    /**
     * 查找业主车位
     * @param page
     * @param limit
     * @param name
     * @return
     */
    @GetMapping("/api/findCar")
    public ResBody findCar(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        ResBody resBody = new ResBody();
        Page<Car> pages= carService.findCar(page, limit,name);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    /**
     * 查找免费车位
     * @param type
     * @return
     */
    @GetMapping("/ajax/getAllFreeCars")
    public ResBody getAllDanyuans(@RequestParam int type) {
        ResBody resBody = new ResBody();
        List<Car> list= carService.getAllFreeCars(type);
        resBody.setData(list);
        resBody.setCode(0);
        return resBody;
    }
}
