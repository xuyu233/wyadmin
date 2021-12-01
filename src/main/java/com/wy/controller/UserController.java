package com.wy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.common.CodecUtils;
import com.wy.config.SendByEmailTools;
import com.wy.pojo.ResBody;
import com.wy.pojo.User;
import com.wy.sdk.GeetestConfig;
import com.wy.sdk.GeetestLib;
import com.wy.sdk.GeetestLibResult;
import com.wy.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/api/getUsers")
    public ResBody getUsers(@RequestParam Long page,
                                   @RequestParam Long limit) {
        ResBody resBody = new ResBody();
        Page<User> pages= userService.getUsers(page, limit);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    /**
     * 极验滑动验证码
     * @param session
     * @return
     */
    @GetMapping("/user/register")
    public String register(HttpSession session){
        GeetestLib gtLib = new GeetestLib(GeetestConfig.GEETEST_ID, GeetestConfig.GEETEST_KEY);
        String userId = "test";
        String digestmod = "md5";
        Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("digestmod", digestmod);
        paramMap.put("user_id", userId);
        paramMap.put("client_type", "web");
        paramMap.put("ip_address", "127.0.0.1");
        GeetestLibResult result = gtLib.register(digestmod, paramMap);
        // 将结果状态写到session中，此处register接口存入session，后续validate接口会取出使用
        // 注意，此demo应用的session是单机模式，格外注意分布式环境下session的应用
        session.setAttribute(GeetestLib.GEETEST_SERVER_STATUS_SESSION_KEY, result.getStatus());
        session.setAttribute("userId", userId);
        // 注意，不要更改返回的结构和值类型
        // 注意，不要更改返回的结构和值类型
        return result.getData();
    }

    /**
     * 业主注册
     * @param user
     * @return
     */
    @PostMapping("/api/addUser")
    public ResBody addUser(@RequestBody User user) {
        ResBody resBody = new ResBody();
        // 密码加密
        int i = userService.addUser(user);
        if (i == 1){
            // 给用户发送一条提示邮箱
            if (userService.sendEmail(user)){
                resBody.setCode(200);
                resBody.setMsg("邮件已发送");
            }
            resBody.setCode(200);
            resBody.setMsg("添加成功");
        }else if(i == -1){
            resBody.setCode(400);
            resBody.setMsg("验证码有误或已过期");
        }else{
            resBody.setCode(500);
            resBody.setMsg("添加失败，手机号已存在");
        }
        return resBody;
    }

    @PostMapping("/api/updateUser")
    public ResBody updateUser(@RequestBody User user) {
        ResBody resBody = new ResBody();
        int i = userService.updateUser(user);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("修改成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("修改失败");
        }
        return resBody;
    }

    @GetMapping("/api/delUser")
    public ResBody delUser(@RequestParam int id) {
        ResBody resBody = new ResBody();
        int i = userService.delUser(id);
        if (i == 1){
            resBody.setCode(200);
            resBody.setMsg("删除成功");
        }else{
            resBody.setCode(500);
            resBody.setMsg("删除失败");
        }
        return resBody;
    }

    @GetMapping("/api/findUser")
    public ResBody findBuilding(@RequestParam Long page,
                                @RequestParam Long limit,
                                @RequestParam String name) {
        ResBody resBody = new ResBody();
        Page<User> pages= userService.findUser(page, limit,name);
        resBody.setCount(pages.getTotal());
        resBody.setData(pages.getRecords());
        resBody.setCode(0);
        return resBody;
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping("/api/sendCode")
    public ResBody sendCode(@RequestBody String phone){
        ResBody resBody = new ResBody();
        String code = userService.sendCode(phone);

        if (StringUtils.isNotBlank(code)){
            resBody.setCode(200);
            resBody.setData(Arrays.asList(code));
            resBody.setMsg("短信验证码已发送！"+code);
        }else{
            resBody.setCode(500);
            resBody.setMsg("短信验证码发送失败！");
        }
        return resBody;
    }

    /**
     * 业主登录
     * @param params
     * @param session
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/api/loginByPassword")
    public ResBody loginByPassword(@RequestBody Map<String, Object> params,
                                   HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        ResBody resBody = new ResBody();
        String phone = params.get("phone").toString();
        String password = params.get("password").toString();

        //验证是否存在此手机号
        int count = userService.findUserByPhone(phone);
        if (count==0){
            resBody.setCode(400);
            resBody.setMsg("抱歉，此手机号未注册");
            return resBody;
        }

        User user = userService.loginByPassword(phone,password);
        if (user == null){
            resBody.setCode(500);
            resBody.setMsg("账号或密码错误");
        }else {
            session.setAttribute("user",user);
            System.out.println("userPhone = " + user.getPhone());
            System.out.println("userId = " + user.getId());

            session.setAttribute("user",user);
            session.setMaxInactiveInterval(7200);

            resBody.setCode(200);
            resBody.setMsg("登录成功");
        }
        return resBody;
    }

    /**
     * 业主修改密码
     * @param params
     * @param session
     * @return
     */
    @PostMapping("/api/updatePass")
    public ResBody updatePass(@RequestBody Map<String, Object> params,
                              HttpSession session) {
        ResBody resBody = new ResBody();
        User user = (User) session.getAttribute("user");
        String oldPsw = params.get("oldPsw").toString();
        if(!StringUtils.equals(CodecUtils.md5Hex(oldPsw,user.getSalt()),user.getPassword())){
            resBody.setCode(500);
            resBody.setMsg("原始密码输入错误");
        }else {
            String newPsw = params.get("newPsw").toString();
            user.setPassword(newPsw);
            int i = userService.updatePass(user.getId(), newPsw);
            if (i != 1) {
                resBody.setCode(500);
                resBody.setMsg("修改失败，后台出错");
            } else {
                session.setAttribute("user", user);
                resBody.setCode(200);
                resBody.setMsg("修改成功");
            }
        }
        return resBody;
    }
}
