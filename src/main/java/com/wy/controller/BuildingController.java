package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Building;
import com.wy.pojo.ResBody;
import com.wy.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BuildingController {
    @Resource
    private BuildingService buildingService;

    @GetMapping("/api/getAllBuildings")
    public ResBody getAllBuildings(@RequestParam Long page,
                               @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Building> pages= buildingService.getAllBuildings(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/addBuilding")
    public ResBody addBuilding(@RequestBody Building building) {
        ResBody resBody = new ResBody();
        int i = buildingService.addBuilding(building);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @PostMapping("/api/updateBuilding")
    public ResBody updateBuilding(@RequestBody Building building) {
        ResBody resBody = new ResBody();
        int i = buildingService.updateBuilding(building);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delBuilding")
    public ResBody delBuilding(@RequestParam Integer id) {
        ResBody resBody = new ResBody();
        int i = buildingService.delBuilding(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findBuilding")
    public ResBody findBuilding(@RequestParam Long page,
                            @RequestParam Long limit,
                            @RequestParam String name) {
        ResBody resBody = new ResBody();
        Page<Building> pages= buildingService.findBuilding(page, limit,name);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/ajax/getAllBuildings")
    public ResBody getAllDanyuans() {
        ResBody resBody = new ResBody();
        List<Building> list= buildingService.getAllBuildings();
        resBody.setData(list);
        resBody.setCode(0);
        return resBody;
    }
}
