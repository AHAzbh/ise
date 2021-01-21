package com.ise.demo.service;

import com.ise.demo.pojo.Good;

import java.util.List;
import java.util.Optional;

public interface GoodService {
    public List<Good> findAll() throws Exception;
    public Optional<Good> findById(String id)throws Exception;
    public Good save(Good good)throws Exception;
    public void deleteById(String id)throws Exception;
    public void addGood(Good good)throws Exception;
    public boolean sell(String gid,String uname,int num)throws Exception;


}
