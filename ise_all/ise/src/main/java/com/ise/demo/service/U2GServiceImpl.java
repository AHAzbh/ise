package com.ise.demo.service;

import com.ise.demo.dao.GoodDao;
import com.ise.demo.dao.U2GDao;
import com.ise.demo.exception.DaoException;
import com.ise.demo.pojo.U2G;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("u2GService")
public class U2GServiceImpl implements U2GService {
    @Resource(name = "u2GDao")
    private U2GDao u2GDao;


    public List<U2G> findByUname(String uname) throws Exception{
        List<U2G> u2GList;
        try {
             u2GList = u2GDao.findByUname(uname);
        }catch(Exception e){
            throw new DaoException("U2GDao findByUname() 出错");
        }
        for(U2G u : u2GList){
            u.setUname("");
        }
        return u2GList;
    }
}
