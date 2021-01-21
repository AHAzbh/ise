package com.ise.demo.service;

import com.ise.demo.dao.GoodDao;
import com.ise.demo.exception.DaoException;
import com.ise.demo.pojo.Good;
import org.springframework.stereotype.Repository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository("goodService")
public class GoodServiceImpl implements GoodService {
    @Resource(name = "goodDao")
    private GoodDao goodDao;
    @Resource
    private RedisService redisService;
    @Resource
    private RabbitTemplate rabbitTemplate;



    public List<Good> findAll() throws Exception{
        try{
            return goodDao.findAll();
        }catch(Exception e){
            throw new DaoException("goodDao findAll() 出错");
        }
    }
    public Optional<Good> findById(String id) throws Exception{
        try{
            return goodDao.findById(id);
        }catch(Exception e){
            throw new DaoException("goodDao findById() 出错");
        }
    }
    public Good save(Good good) throws Exception{
        try{
            return goodDao.save(good);
        }catch(Exception e){
            throw new DaoException("goodDao save() 出错");
        }
    }
    public void deleteById(String id) throws Exception{
        try{
            goodDao.deleteById(id);
        }catch(Exception e){
            throw new DaoException("goodDao deleteById() 出错");
        }
    }
    public void addGood(Good good) throws Exception{
        String id = good.getGid();
        Optional<Good> g = findById(id);
        if(g.isEmpty()){
            save(good);
        }else{
            System.out.println(good.getGid()+"already exists.");
        }
    }

    //对用户购买商品做处理
    public boolean sell(String gid,String uname,int num) throws Exception{
        Map<String,Object> map=new HashMap<>();
        map.put("uname",uname);
        map.put("gid",gid);
        map.put("num",num);
        Object remain = redisService.getValue(gid);
        if(remain != null && (int)remain > 0){
            //生产消息，添加到队列
            rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
            return true;
        }
        Optional<Good> g = findById(gid);
        if(g.isEmpty()) return false;
        if(g.get().getTime().after(new Date())) return false;
        redisService.addKey(gid,g.get().getNum(),60, TimeUnit.SECONDS);
        //生产消息，添加到队列里
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return true;
    }



}
