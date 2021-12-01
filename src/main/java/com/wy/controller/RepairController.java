package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Repair;
import com.wy.pojo.ResBody;
import com.wy.pojo.User;
import com.wy.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RepairController {
    @Resource
    private RepairService repairService;

    @GetMapping("/api/getAllRepairs")
    public ResBody getAllRepairs(@RequestParam Long page,
                                   @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Repair> pages= repairService.getAllRepairs(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/api/getAllRepairsByUser")
    public ResBody getAllRepairsByUser(@RequestParam Long page,
                                      @RequestParam Long limit, HttpSession session){
        ResBody resBody = new ResBody();
        User user = (User) session.getAttribute("user");
        Page<Repair> pages= repairService.getAllRepairsByUser(page, limit,user.getId());
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/addRepair")
    public ResBody addRepair(@RequestBody Repair repair,HttpSession session) {
        ResBody resBody = new ResBody();
        User user = (User) session.getAttribute("user");
        if (user!=null){
            repair.setUserId(user.getId());
        }
        int i = repairService.addRepair(repair);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @PostMapping("/api/updateRepair")
    public ResBody updateRepair(@RequestBody Repair repair) {
        ResBody resBody = new ResBody();
        int i = repairService.updateRepair(repair);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delRepair")
    public ResBody delRepair(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = repairService.delRepair(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findRepair")
    public ResBody findRepair(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        ResBody resBody = new ResBody();
        Page<Repair> pages = null;
        if (name.isEmpty()){
             pages= repairService.getAllRepairs(page, limit);
        }else {
             pages= repairService.findRepair(page, limit,name);
        }
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }
}
