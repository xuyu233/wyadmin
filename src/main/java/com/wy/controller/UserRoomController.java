package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.ResBody;
import com.wy.pojo.UserRoom;
import com.wy.service.UserRoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserRoomController {
    @Resource
    private UserRoomService userRoomService;

    @GetMapping("/api/findRoomRecordById")
    public ResBody findRoomRecordById(@RequestParam int id,@RequestParam Long page,@RequestParam Long limit){
        ResBody resBody = new ResBody();
        Page<UserRoom> pages= userRoomService.findRoomRecordById(id,page,limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/api/stopRoomByUserId")
    public ResBody stopRoomByUserId(@RequestParam int id){
        ResBody resBody = new ResBody();
        int i = userRoomService.stopRoomByUserId(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("失败");
        }
        return resBody;
    }

    @PostMapping("/api/fenpeiRoom")
    public ResBody fenpeiRoom(@RequestBody UserRoom user_room) {
        ResBody resBody = new ResBody();
        user_room.setUserId(user_room.getId());
        //判断该用户当前有无房间，如果有，先退房再入住。
        int count = userRoomService.findRoom(user_room.getUserId());
        if (count == 1){
            userRoomService.outRoom(user_room.getUserId());
        }
        int i = userRoomService.fenpei(user_room.getUserId(),user_room.getRoomId());
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
