package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.ResBody;
import com.wy.pojo.Tousu;
import com.wy.pojo.User;
import com.wy.service.TousuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 投诉管理
 */
@RestController
public class TousuController {
    @Resource
    private TousuService tousuService;

    @GetMapping("/api/getAllTousus")
    public ResBody getAllTousus(@RequestParam Long page,
                                   @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Tousu> pages= tousuService.getAllTousus(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/api/getAllToususByUser")
    public ResBody getAllToususByUser(@RequestParam Long page,
                                      @RequestParam Long limit, HttpSession session){
        ResBody resBody = new ResBody();
        User user = (User) session.getAttribute("user");
        Page<Tousu> pages= tousuService.getAllToususByUser(page, limit,user.getId());
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/addTousu")
    public ResBody addTousu(@RequestBody Tousu tousu,HttpSession session) {
        ResBody resBody = new ResBody();
        User user = (User) session.getAttribute("user");
        if (user!=null){
            tousu.setUserId(user.getId());
        }
        int i = tousuService.addTousu(tousu);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @PostMapping("/api/updateTousu")
    public ResBody updateTousu(@RequestBody Tousu tousu) {
        ResBody resBody = new ResBody();
        int i = tousuService.updateTousu(tousu);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delTousu")
    public ResBody delTousu(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = tousuService.delTousu(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findTousu")
    public ResBody findTousu(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        ResBody resBody = new ResBody();
        int count = 0;
        Page<Tousu> pages= null;
        if (name.isEmpty()){
            pages= tousuService.getAllTousus(page, limit);
        }else {
            pages= tousuService.findTousu(page, limit,name);
        }
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

}
