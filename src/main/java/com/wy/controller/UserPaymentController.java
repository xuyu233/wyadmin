package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.*;
import com.wy.service.UserPaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class UserPaymentController {
    @Resource
    private UserPaymentService userPaymentService;

    @GetMapping("/api/getAllPaymentDetails")
    public ResBody getAllPaymentDetails(@RequestParam Long page,
                                        @RequestParam Long limit, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page<UserPayment> pages = null;
        if (user!=null){
            int user_id=user.getId();
            pages= userPaymentService.getAllPaymentDetails(page, limit,user_id);
        }else {
            pages= userPaymentService.getAllPaymentDetails(page, limit);
        }
        ResBody resBody = new ResBody();
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/api/findPaymentDetail")
    public ResBody findPaymentDetail(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        int count = 0;
        Page<UserPayment> pages= null;
        ResBody resBody = new ResBody();
        if (name.isEmpty()){
            pages= userPaymentService.getAllPaymentDetails(page, limit);
        }else {
            pages= userPaymentService.getAllPaymentDetails(page, limit,name);
        }
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/fenpeiPayment")
    public ResBody fenpeiPayment(@RequestBody UserPayment UserPayment) {
        ResBody resBody = new ResBody();
        UserPayment.setUserId(UserPayment.getId());
        int i = userPaymentService.fenpei(UserPayment.getUserId(),UserPayment.getPaymentId(),UserPayment.getValue());
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @GetMapping("/api/jiaofei")
    public ResBody jiaofei(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = userPaymentService.jiaofei(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("成功");
        }else {
            resBody.setCode(500);
            resBody.setMsg("失败");
        }
        return resBody;
    }
}
