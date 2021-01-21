package com.ise.demo.service;

import com.ise.demo.pojo.U2G;

import java.util.List;

public interface U2GService {
    public List<U2G> findByUname(String uname) throws Exception;

}
