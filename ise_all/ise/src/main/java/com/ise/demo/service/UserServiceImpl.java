package com.ise.demo.service;

import com.ise.demo.dao.UserDao;
import com.ise.demo.exception.DaoException;
import com.ise.demo.pojo.User;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository("userService")
public class UserServiceImpl implements UserService {
    @Resource(name = "userDao")
    private UserDao userDao;

    public List<User> findAll()throws Exception
    {
        try{
            return userDao.findAll();
        }catch(Exception e){
            throw new DaoException("userDao findAll() 出错");
        }
    }
    public Optional<User> findById(String id)throws Exception
    {
        try{
            return userDao.findById(id);
        }catch(Exception e){
            throw new DaoException("userDao findById() 出错");
        }
    }
    public User save(User user)throws Exception
    {
        try{
            return userDao.save(user);
        }catch(Exception e){
            throw new DaoException("userDao save() 出错");
        }
    }
    public void deleteById(String id)throws Exception
    {
        try{
            userDao.deleteById(id);
        }catch(Exception e){
            throw new DaoException("userDao deleteById() 出错");
        }
    }

    //登录认证
    public boolean login(String unameBefMd5, String passwordBefMd5, HttpSession session, Model model)
    throws Exception{
        String uname = DigestUtils.md5DigestAsHex(unameBefMd5.getBytes());
        String password = DigestUtils.md5DigestAsHex(passwordBefMd5.getBytes());
        Optional<User> u = findById(uname);
        if(u.isEmpty()){
            model.addAttribute("msg","用户名或密码错误");
            return false;
        }
        if(u.get().getPassword().equals(password)){
            session.setAttribute("uname",uname);
            session.setAttribute("utype",u.get().getUtype());
            return true;
        }
        model.addAttribute("msg","用户名或密码错误");
        return false;
    }
    //用户注册
    public boolean register(String unameBefMd5,String passwordBefMd5,HttpSession session,Model model)
    throws Exception{
        String uname = DigestUtils.md5DigestAsHex(unameBefMd5.getBytes());
        String password = DigestUtils.md5DigestAsHex(passwordBefMd5.getBytes());
        Optional<User> u = findById(uname);
        if(u.isPresent()){
            model.addAttribute("msg","用户名已被占用");
            return false;
        }
        User user = new User();
        user.setUname(uname);
        user.setPassword(password);
        user.setUtype("buyer");
        save(user);
        model.addAttribute("msg","注册成功，请登录");
        return true;
    }


}
