package com.rabbitmqconsumer.demo;

import com.rabbitmqconsumer.demo.dao.GoodDao;
import com.rabbitmqconsumer.demo.dao.U2GDao;
import com.rabbitmqconsumer.demo.pojo.Good;
import com.rabbitmqconsumer.demo.pojo.U2G;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver {
    @Resource(name = "goodDao")
    private GoodDao goodDao;
    @Resource(name = "u2GDao")
    private U2GDao u2GDao;

    //订阅并处理消息
    @RabbitHandler
    public void process(Map testMessage) {
        Object _gid = testMessage.get("gid"),
                _num = testMessage.get("num"),
                _uname = testMessage.get("uname");
        if(_gid == null || _num == null || _uname == null) return;
        String gid = (String)_gid,uname = (String)_uname;
        int num = (int)_num;
        Optional<Good> og = goodDao.findById(gid);
        if(og.isEmpty()) return;
        U2G u = new U2G();
        Good g = og.get();
        u.setUname(uname);
        u.setGid(gid);
        u.setNum(num);
        u.setTime(new Date());
        if(g.getNum() < num){
            u.setDescription("商品已售罄，购买失败");
        }else{
            g.setNum(g.getNum() - num);
            goodDao.save(g);
            u.setDescription("购买成功");
        }
        u.setImg(g.getImg());
        u.setGname(g.getGname());
        u2GDao.save(u);
    }

}