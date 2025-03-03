package com.snnubus.service.impl;

import com.snnubus.dao.UserDao;
import com.snnubus.entity.User;
import com.snnubus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public int insertId(String userId,String userName,String userSchool) {
        return userDao.insertId(userId,userName,userSchool);
    }
    @Override
    public int updateinfo(String userName,String userSex,String userTelephone,String openid,String gschool)
    {
        return userDao.updateinfo(userName,userSex,userTelephone,openid,gschool);
    }

    @Override
    public List<User> getById(String openid,String gschool) {
        return userDao.getById(openid,gschool);
    }
    @Override
    public List<User> getinfo(String openid,String gschool) {
        return userDao.getinfo(openid,gschool);
    }

}
