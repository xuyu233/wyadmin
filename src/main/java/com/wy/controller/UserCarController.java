package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.ResBody;
import com.wy.pojo.UserCar;
import com.wy.service.UserCarService;
import com.wy.service.UserCarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户车位
 */
@RestController
public class UserCarController {
    @Resource
    private UserCarService userCarService;

    @GetMapping("/api/findCarRecordById")
    public ResBody findCarRecordById(@RequestParam Long id,@RequestParam Long page,@RequestParam Long limit){
        ResBody resBody = new ResBody();
        Page<UserCar> pages= userCarService.findCarRecordById(id,page,limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/api/stopCarByUserId")
    public ResBody stopCarByUserId(@RequestParam int id){
        ResBody resBody = new ResBody();
        int i = userCarService.stopCarByUserId(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("失败");
        }
        return resBody;
    }

    @PostMapping("/api/fenpeiCar")
    public ResBody fenpeiCar(@RequestBody UserCar UserCar) {
        ResBody resBody = new ResBody();
        System.out.println(UserCar);
        UserCar.setUserId(UserCar.getId());
        //判断该用户当前有无房间，如果有，先退房再入住。
        int count = userCarService.findCar(UserCar.getUserId());
        if (count == 1){
            userCarService.outCar(UserCar.getUserId());
        }
        int i = userCarService.fenpei(UserCar.getUserId(),UserCar.getCarId());
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }
}
