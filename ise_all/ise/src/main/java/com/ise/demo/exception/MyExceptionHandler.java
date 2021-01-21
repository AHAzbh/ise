package com.ise.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if(e instanceof DaoException){
            log.error("Dao层错误",e);
            return new ModelAndView("/error");
        }else if(e instanceof ServiceException){
            log.error("Service层错误",e);
            return new ModelAndView("/error");
        }else{
            log.error("未知错误",e);
            return new ModelAndView("/error");
        }
    }
}
