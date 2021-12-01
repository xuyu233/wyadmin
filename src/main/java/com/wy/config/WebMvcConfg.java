package com.wy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfg implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/page/template/login");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //网站配置生成器：添加一个拦截器，拦截路径为整个项目
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/login",
                "/user/login",
                "/admin/loginByPassword",
                "/api/loginByPassword",
                "/api/addUser",
                "/assets/**"  ,         //html静态资源
                "/images/**" ,          //html静态资源
                "/json/**"  ,        //html静态资源
                "/api/sendCode",          //发送短信
                "/user/register",          //极验
                "/user/sendMail"          //邮箱

        );
    }
}