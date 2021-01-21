package com.ise.demo.service;

import com.ise.demo.pojo.Permission;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface PerService {
    public List<Permission> findAll() throws Exception;
    public Optional<Permission> findById(String id)throws Exception;
    public Permission save(Permission permission)throws Exception;
    public void deleteById(String id)throws Exception;
    public List<Permission> findAllByType(String type)throws Exception;
    public boolean judgePer(String type,String httpUrl)throws Exception;
    public HashMap<String,String> findByUserAndPerType(String utype, String pertype) throws Exception;

}
