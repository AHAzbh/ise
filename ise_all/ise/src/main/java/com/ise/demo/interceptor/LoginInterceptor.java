package com.ise.demo.interceptor;

import com.ise.demo.dao.PermissionDao;
import com.ise.demo.service.PerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /* 对用户是否登录做拦截，用户如果没有登录就尝试访问主页就弹回登录页面 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        log.info("============= LoginInterceptor Start =============");
        log.info("URL               : {}",url);
        log.info("Request IP        : {}",request.getRemoteAddr());
        log.info("HTTP Method       : {}",request.getMethod());
        Map<String,String[]> m = request.getParameterMap();
        StringBuilder requestPara = new StringBuilder();
        StringBuilder requestValue = new StringBuilder();
        for(Map.Entry<String,String[]> entry : m.entrySet()){
            requestValue.delete(0,requestValue.length());
            requestValue.append("{");
            for(String s : entry.getValue()){
                requestValue.append(s).append(" ");
            }
            requestValue.delete(requestValue.length()-1,requestValue.length());
            requestValue.append("}");
            requestPara.append(String.format("<%s,%s> ", entry.getKey(), requestValue));
        }
        log.info("Handler Class     : {}",handler.getClass());
        if(handler instanceof  HandlerMethod) {
            log.info("Handler Method    : {}", ((HandlerMethod) handler).getMethod());
        }
        log.info("Request Args      : {}",requestPara);
        if(url.contains("/login") || url.contains("/register")||
                url.contains("/toLogin") || url.contains("/toRegister")){
            //System.out.println("loginIcp url contains true");
            return true;
        }
        HttpSession session = request.getSession();
        String uname = (String)session.getAttribute("uname");
        if(uname != null) {
            return true;
        }
        System.out.println("here1");
        request.setAttribute("msg","还没有登录，请先登录！");
        request.getRequestDispatcher("/login").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("============== LoginInterceptor End ==============");
    }
}
