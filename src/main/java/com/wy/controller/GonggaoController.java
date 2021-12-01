package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Admin;
import com.wy.pojo.Gonggao;
import com.wy.pojo.ResBody;
import com.wy.service.GonggaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class GonggaoController {
    @Resource
    private GonggaoService gonggaoService;

    @GetMapping("/api/getAllGonggaos")
    public ResBody getAllGonggaos(@RequestParam Long page,
                                   @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Gonggao> pages= gonggaoService.getAllGonggaos(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/api/getAllShowGonggaos")
    public ResBody getAllShowGonggaos() {
        ResBody resBody = new ResBody();
        List<Gonggao> list= gonggaoService.getAllShowGonggaos();
        resBody.setData(list);
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/addGonggao")
    public ResBody addGonggao(@RequestBody Gonggao gonggao, HttpSession session) {
        ResBody resBody = new ResBody();
        Admin admin = (Admin) session.getAttribute("admin");
        gonggao.setCreatetime(new Date());
        gonggao.setCreateby(admin.getId());
        gonggao.setUpdatetime(new Date());
        int i = gonggaoService.addGonggao(gonggao);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @PostMapping("/api/updateGonggao")
    public ResBody updateGonggao(@RequestBody Gonggao gonggao, HttpSession session) {
        ResBody resBody = new ResBody();
        Admin admin = (Admin) session.getAttribute("admin");
        gonggao.setUpdatetime(new Date());
        gonggao.setUpdateby(admin.getId());
        int i = gonggaoService.updateGonggao(gonggao);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delGonggao")
    public ResBody delBuilding(@RequestParam Integer id) {
        ResBody resBody = new ResBody();
        int i = gonggaoService.delGonggao(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findGonggao")
    public ResBody findBuilding(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        ResBody resBody = new ResBody();
        Page<Gonggao> pages= gonggaoService.findGonggao(page, limit,name);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }
}
