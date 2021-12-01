package com.wy.controller;

import com.wy.common.CodecUtils;
import com.wy.pojo.Admin;
import com.wy.pojo.ResBody;
import com.wy.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class AdminController {
    @Resource
    private AdminService adminService;

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    /**
     * 管理员登录
     * @param params
     * @param session
     * @return
     */
    @PostMapping("/admin/loginByPassword")
    public ResBody loginByPassword(@RequestBody Map<String, Object> params,
                                   HttpSession session) {
        ResBody resBody = new ResBody();
        String email = params.get("email").toString();
        String password = params.get("password").toString();

        int count = adminService.findAdminByEmail(email);
        if (count==0){
            resBody.setCode(400);
            resBody.setMsg("管理员邮箱不正确");
            return resBody;
        }

        Admin admin = adminService.findAdmin(email,password);
        if (admin == null){
            resBody.setCode(500);
            resBody.setMsg("管理员密码不正确");
        }else {
            session.setAttribute("admin",admin);
            LOG.info(admin.toString());
            resBody.setCode(200);
            resBody.setMsg("登录成功");
        }
        return resBody;
    }

    /**
     * 管理员密码更新
     * @param params
     * @param session
     * @return
     */
    @PostMapping("/admin/updatePass")
    public ResBody updatePass(@RequestBody Map<String, Object> params,
                              HttpSession session) {
        ResBody resBody = new ResBody();
        String oldPsw = params.get("oldPsw").toString();
        Admin admin = (Admin) session.getAttribute("admin");
        if(!StringUtils.equals(CodecUtils.md5Hex(oldPsw,admin.getSalt()),admin.getPassword())){
            resBody.setCode(500);
            resBody.setMsg("原始密码输入错误");
        }else{
            String newPsw = params.get("newPsw").toString();
            admin.setPassword(CodecUtils.md5Hex(newPsw,admin.getSalt()));
            int i = adminService.updatePass(admin);
            if (i != 1){
                resBody.setCode(500);
                resBody.setMsg("修改失败，后台出错");
            }else {
                session.setAttribute("admin",admin);
                LOG.info(admin.toString());
                resBody.setCode(200);
                resBody.setMsg("修改成功");
            }
        }
        return resBody;
    }
}
