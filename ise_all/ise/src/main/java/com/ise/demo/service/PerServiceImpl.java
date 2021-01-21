package com.ise.demo.service;

import com.ise.demo.dao.PermissionDao;
import com.ise.demo.exception.DaoException;
import com.ise.demo.pojo.Good;
import com.ise.demo.pojo.Permission;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("perService")
public class PerServiceImpl implements PerService {
    @Resource(name = "permissionDao")
    private PermissionDao permissionDao;

    public List<Permission> findAll()throws Exception
    {
        try{
            return permissionDao.findAll();
        }catch(Exception e){
            throw new DaoException("permissionDao deleteById() 出错");
        }
    }
    public Optional<Permission> findById(String id)throws Exception
    {
        try{
            return permissionDao.findById(id);
        }catch(Exception e){
            throw new DaoException("permissionDao findById() 出错");
        }
    }
    public Permission save(Permission permission)throws Exception
    {
        try{
            return permissionDao.save(permission);
        }catch(Exception e){
            throw new DaoException("permissionDao save() 出错");
        }
    }
    public void deleteById(String id)throws Exception
    {
        try{
            permissionDao.deleteById(id);
        }catch(Exception e){
            throw new DaoException("permissionDao deleteById() 出错");
        }
    }
    public List<Permission> findAllByType(String type)throws Exception
    {
        try{
            return permissionDao.findAllByType(type);
        }catch(Exception e){
            throw new DaoException("permissionDao findAllByType() 出错");
        }
    }
    public boolean judgePer(String type,String httpUrl)throws Exception
    {
        List<Permission> lp = findAllByType(type);
        for(Permission p : lp){
            if(httpUrl.contains(p.getPerurl())) return true;
        }
        return false;
    }
    public HashMap<String,String> findByUserAndPerType(String utype, String pertype)
    throws Exception{
        List<Permission> per;
        HashMap<String,String> result = new HashMap<>();
        try{
            per = permissionDao.findByUserAndPerType(utype,pertype);
        }catch(Exception e){
            throw new DaoException("permissionDao findByUserAndPerType() 出错");
        }
        for(Permission p : per){
            result.put(p.getPerurl(),p.getPername());
        }
        return result;
    }

}
