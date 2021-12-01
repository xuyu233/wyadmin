package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Danyuan;
import com.wy.pojo.ResBody;
import com.wy.service.DanyuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DanyuanController {
    @Resource
    private DanyuanService danyuanService;

    @GetMapping("/api/getAllDanyuans")
    public ResBody getAllDanyuans(@RequestParam Long page,
                                   @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Danyuan> pages= danyuanService.getAllDanyuans(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/addDanyuan")
    public ResBody addDanyuan(@RequestBody Danyuan danyuan) {
        ResBody resBody = new ResBody();
        int i = danyuanService.addDanyuan(danyuan);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @PostMapping("/api/updateDanyuan")
    public ResBody updateDanyuan(@RequestBody Danyuan danyuan) {
        ResBody resBody = new ResBody();
        int i = danyuanService.updateDanyuan(danyuan);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delDanyuan")
    public ResBody delDanyuan(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = danyuanService.delDanyuan(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findDanyuan")
    public ResBody findBuilding(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        ResBody resBody = new ResBody();
        Page<Danyuan> pages= danyuanService.findDanyuan(page, limit,name);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/ajax/getAllDanyuans")
    public ResBody getAllDanyuans() {
        ResBody resBody = new ResBody();
        List<Danyuan> list= danyuanService.getAllDanyuans();
        resBody.setData(list);
        resBody.setCode(0);
        return resBody;
    }
}
