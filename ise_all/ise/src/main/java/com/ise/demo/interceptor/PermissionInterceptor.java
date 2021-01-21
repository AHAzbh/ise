package com.ise.demo.interceptor;

import com.ise.demo.service.PerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    /* 用户访问权限认证，使用permission表里对应用户类型可访问的url做权限认证*/
    @Resource(name = "perService")
    private PerService perService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        HttpSession session = request.getSession();
        String uname = (String)session.getAttribute("uname");
        String utype = (String)session.getAttribute("utype");
        log.info("========== PermissionInterceptor  Start ==========");
        log.info("User Name         : {}",uname);
        log.info("User Type         : {}",utype);
        if(utype==null || perService.judgePer(utype,url)){
            log.info("User Permission Admitted");
            return true;
        }
        log.info("User Permission Rejected");
        request.setAttribute("msg","还没有登录，请先登录！");
        request.getRequestDispatcher("/login").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Response Status   : {} ",response.getStatus());
        log.info("=========== PermissionInterceptor  End ===========");
    }
}
