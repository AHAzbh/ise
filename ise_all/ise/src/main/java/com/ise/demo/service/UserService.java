package com.ise.demo.service;

import com.ise.demo.pojo.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> findAll() throws Exception;
    public Optional<User> findById(String id)throws Exception;
    public User save(User user)throws Exception;
    public void deleteById(String id)throws Exception;
    public boolean login(String uname, String password, HttpSession session, Model model)throws Exception;
    public boolean register(String uname,String password,HttpSession session,Model model)throws Exception;
    //public HashMap<String,String> findButtonByType(String utype) throws Exception;
}
