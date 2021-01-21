package com.ise.demo;

import com.ise.demo.dao.U2GDao;
import com.ise.demo.pojo.Good;
import com.ise.demo.pojo.Permission;
import com.ise.demo.pojo.U2G;
import com.ise.demo.service.GoodService;
import com.ise.demo.service.PerService;
import com.ise.demo.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@Controller
class DemoApplicationTests {

    @Resource
    private GoodService goodService;
    @Resource
    private PerService perService;
    @Resource
    private RedisService redisService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private U2GDao u2GDao;


    @Test
    void contextLoads() throws Exception {
        /*for(U2G u :u2GDao.findByUname("bob")){
            System.out.println(u.getDescription());
        }
        for(U2G u : u2GDao.findByGid("0001")){
            System.out.println(u.getDescription());
        }*/
        Good g = new Good();
        g.setImg("0001_1");
        g.setTime(new Date());
        g.setNum(100);
        g.setGname("orange");
        g.setGid("0001");
        goodService.save(g);
        /*Optional<Good> g = goodService.findById("0001");
        if(g.isEmpty()) return ;
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(g.get().getTime().after(new Date(System.currentTimeMillis())));*/
        /*redisService.addKey("hello","world",60, TimeUnit.SECONDS);
        System.out.println("redis get : " + redisService.getValue("hello"));
        Map<String,Object> map=new HashMap<>();
        map.put("userName","00011");
        map.put("goodId","gid");
        map.put("num",1);
        redisService.addKey("map",map,60,TimeUnit.SECONDS);
        System.out.println("key map : "+redisService.getValue("map").toString());*/
        /*Map<String,Object> map=new HashMap<>();
        map.put("userName","00011");
        map.put("goodId","gid");
        map.put("num",1);
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        System.out.println("success");*/


    }

}
