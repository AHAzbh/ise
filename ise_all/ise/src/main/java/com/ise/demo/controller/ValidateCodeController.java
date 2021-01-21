package com.ise.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

@Controller
public class ValidateCodeController {
    private final char[] code ={'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t','u',
    'v','w','x','y','z','2','3','4','5','6','7','8','9'};
    private static final int WIDTH=100;
    private static final int HEIGHT=40;
    private static final int LENGTH=4;
    //画验证码
    @RequestMapping(value="/validateCode")
    public void validateCode(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException{
        //设置响应报头信息
        response.setHeader("Pragma","Np-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        //设置响应的MIME类型
        response.setContentType("image/jpeg");
        BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        Font mFont = new Font("Arial", Font.PLAIN,24);
        Graphics g = image.getGraphics();
        Random rd = new Random();

        //设置背景颜色
        g.setColor(new Color(rd.nextInt(55) + 200,rd.nextInt(55) + 200,
                rd.nextInt(55) + 200));
        g.fillRect(0,0,WIDTH,HEIGHT);
        //设置字体
        g.setFont(mFont);
        //画边框
        g.setColor(Color.black);
        g.drawRect(0,0,WIDTH-1,HEIGHT-1);
        //随机产生验证码
        StringBuilder result= new StringBuilder();
        for(int i = 0;i<LENGTH;++i){
            result.append(code[rd.nextInt(code.length)]);
        }
        HttpSession session = request.getSession();
        System.out.println("new validateCode :"+result);
        session.setAttribute("validateCode",result);
        session.setAttribute("validateCodeTime",new Date());
        //画验证码
        for(int i =0;i<result.length();++i){
            g.setColor(new Color(rd.nextInt(200),rd.nextInt(200),
                    rd.nextInt(200)));
            g.drawString(result.charAt(i) + "",20*i+1,24);
        }
        for(int i = 0;i<10;++i){
            g.setColor(new Color(rd.nextInt(200),rd.nextInt(200),
                    rd.nextInt(200)));
            int x1 = rd.nextInt(WIDTH);
            int x2 = rd.nextInt(WIDTH);
            int y1 = rd.nextInt(HEIGHT);
            int y2 = rd.nextInt(HEIGHT);
            g.drawLine(x1,y1,x2,y2);
        }
        //释放图形资源
        g.dispose();
        try{
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"JPEG",os);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
