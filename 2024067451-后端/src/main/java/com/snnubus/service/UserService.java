package com.snnubus.service;

import com.snnubus.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public int insertId(String userId,String userName,String userSchool);
    public int updateinfo(String userName,String userSex,String userTelephone,String openid,String gschool);

    public List<User> getById(String openid,String gschool);
    public List<User> getinfo(String openid,String gschool);

}
