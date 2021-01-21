package com.ise.demo.controller;


import com.ise.demo.exception.ServiceException;
import com.ise.demo.pojo.U2G;
import com.ise.demo.service.GoodService;
import com.ise.demo.service.U2GService;
import com.ise.demo.validator.NumLetterValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class BuyerController {
    @Resource(name = "goodService")
    private GoodService goodService;
    @Resource(name = "u2GService")
    private U2GService u2GService;
    @Resource
    private NumLetterValidator numLetterValidator;

    //数据格式检查，调用Service层处理
    @RequestMapping(value = "/buy")
    @ResponseBody
    public String buy(@RequestParam(value = "gid")String gid,
                      @RequestParam(value = "num")int num,
                      HttpSession session, Model model)
    throws Exception{
        if(!numLetterValidator.judge(gid,32)){
            return "gid 格式错误";
        }
        if(num < 0 || num > 2){
            return "num 格式错误";
        }
        try{
            if(goodService.sell(gid,(String)session.getAttribute("uname"),num)){
                return "抢购成功，排队中，请等待系统处理。";
            }return "出错啦，请重试。";
        }catch(Exception e){
            throw new ServiceException("goodService sell() 出错");
        }
    }
    //查询订单
    @RequestMapping(value = "/checkOrder")
    public String checkOrder(HttpSession session,Model model)
    throws Exception{
        try{

            model.addAttribute("orderList",u2GService.findByUname((String)session.getAttribute("uname")));
            return "showOrder";
        }catch(Exception e){
            throw new ServiceException("u2gService 出错");
        }
    }

}
