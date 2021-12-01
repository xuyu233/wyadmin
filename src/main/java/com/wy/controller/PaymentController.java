package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Payment;
import com.wy.pojo.ResBody;
import com.wy.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @GetMapping("/api/getAllPayments")
    public ResBody getAllPayments(@RequestParam Long page,
                                   @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<Payment> pages= paymentService.getAllPayments(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @PostMapping("/api/addPayment")
    public ResBody addBuilding(@RequestBody Payment payment) {
        ResBody resBody = new ResBody();
        int i = paymentService.addPayment(payment);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败");
        }
        return resBody;
    }

    @PostMapping("/api/updatePayment")
    public ResBody updatePayment(@RequestBody Payment payment) {
        ResBody resBody = new ResBody();
        int i = paymentService.updatePayment(payment);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delPayment")
    public ResBody delPayment(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = paymentService.delPayment(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findPayment")
    public ResBody findPayment(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        ResBody resBody = new ResBody();
        Page<Payment> pages= paymentService.findPayment(page, limit,name);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    @GetMapping("/ajax/getAllPayments")
    public ResBody getAllPayments() {
        ResBody resBody = new ResBody();
        List<Payment> list= paymentService.getAllPayments();
        resBody.setData(list);
        resBody.setCode(0);
        return resBody;
    }
}
