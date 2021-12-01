package com.wy.config;

import com.wy.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        // 此处实现无状态登录

        Object user = request.getSession().getAttribute("user");
        if (user==null){
            user = request.getSession().getAttribute("admin");
        }
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        if (user==null && (!request.getRequestURI().equals("/")) ){
            request.setAttribute("msg","没有权限请先登录");
            request.getRequestDispatcher("/user/login").forward(request,response);
            return false;
        }

        return true;
    }

}
