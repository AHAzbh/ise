package com.ise.demo.configuration;

import com.ise.demo.interceptor.LoginInterceptor;
import com.ise.demo.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyAdapter implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**","/validateCode");
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**","/validateCode");
    }
}
