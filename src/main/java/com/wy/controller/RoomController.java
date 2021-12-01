package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.ResBody;
import com.wy.pojo.Room;
import com.wy.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {
    @Resource
    private RoomService roomService;

    @GetMapping("/api/getAllRooms")
    public ResBody getAllRooms(@RequestParam Long page,
                                  @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Room> pages= roomService.getAllRooms(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/addRoom")
    public ResBody addRoom(@RequestBody Room room) {
        ResBody resBody = new ResBody();
        int i = roomService.addRoom(room);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @PostMapping("/api/updateRoom")
    public ResBody updateDanyuan(@RequestBody Room room) {
        ResBody resBody = new ResBody();
        int i = roomService.updateRoom(room);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delRoom")
    public ResBody delRoom(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = roomService.delRoom(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findRoom")
    public ResBody findBuilding(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        int count = 0;
        Page<Room> pages=  null;
        ResBody resBody = new ResBody();
        if (name.isEmpty()){
            pages= roomService.getAllRooms(page, limit);
        }else {
            pages= roomService.findRoom(page, limit,name);
        }
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/ajax/getAllFreeRooms")
    public ResBody getAllFreeRooms(@RequestParam int danyuan_id) {
        ResBody resBody = new ResBody();
        List<Room> list = roomService.getAllFreeRooms(danyuan_id);
        resBody.setData(list);
        resBody.setCode(0);
        return resBody;
    }
}
