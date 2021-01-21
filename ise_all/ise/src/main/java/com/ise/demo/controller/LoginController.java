package com.ise.demo.controller;


import com.ise.demo.exception.ServiceException;
import com.ise.demo.service.GoodService;
import com.ise.demo.service.PerService;
import com.ise.demo.service.UserService;
import com.ise.demo.validator.NumLetterValidator;
import com.sun.jdi.event.ExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Controller
public class LoginController {
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "goodService")
    private GoodService goodService;
    @Resource(name = "perService")
    private PerService perService;

    @Resource
    private NumLetterValidator numLetterValidator;

    //系统登录入口
    @RequestMapping("/login")
    public String login(){
        //log.info("function login() has been called");
        return "login";
    }
    //用户输入口令，登录认证
    @RequestMapping("/toLogin")
    public String toLogin(@RequestParam(value="uname")String uname,
                          @RequestParam(value="password") String password,
                          @RequestParam(value = "validateCode") String validateCode,
                          HttpSession session, Model model)
    throws Exception{
        if(!numLetterValidator.judge(uname,10)){
            return "login";
        }
        if(!numLetterValidator.judge(password,32)){
            return "login";
        }
        Object _checkCode = session.getAttribute("validateCode"),
                _codeTime = session.getAttribute("validateCodeTime");
        if(_checkCode == null || _codeTime == null){
            model.addAttribute("msg", "验证码已失效，请重新输入！");
            return "login";
        }
        if(validateCode == null || validateCode.equals("") || !(((StringBuilder)_checkCode).toString()).equalsIgnoreCase(validateCode)){
            model.addAttribute("msg", "验证码错误，请重新输入！");
            return "login";
        }
        if (((new Date()).getTime() - ((Date)_codeTime).getTime())/1000/60>5) {
            //验证码有效时长为5分钟
            model.addAttribute("msg", "验证码已失效，请重新输入！");
            return "login";
        }
        try {
            if(userService.login(uname, password, session, model)){
                model.addAttribute("goods",goodService.findAll());
                session.setAttribute("urlMap",
                    perService.findByUserAndPerType
                        ((String)session.getAttribute("utype"), "button"));
                return "main";
            }
            return "login";
        }catch(Exception e){
            throw new ServiceException("Service 出错");
        }
    }

    //跳转到注册界面
    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    //用户注册
    @RequestMapping("/toRegister")
    public String toRegister(@RequestParam(value="uname")String uname,
                             @RequestParam(value="password") String password,
                             HttpSession session, Model model)
    throws Exception{
        if(!numLetterValidator.judge(uname,10)){
            return "register";
        }
        if(!numLetterValidator.judge(password,32)){
            return "register";
        }
        try{
            if(userService.register(uname,password,session,model)){
                return "login";
            }
            return "register";
        }catch(Exception e){
            throw new ServiceException("userService register() 出错");
        }
    }

    //返回主页
    @RequestMapping("/main")
    public String main(Model model) throws Exception {
        try {
            model.addAttribute("goods", goodService.findAll());
            return "main";
        }catch (Exception e){
            throw new ServiceException("goodService findAll() 出错");
        }
    }
    //注销
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }



}
