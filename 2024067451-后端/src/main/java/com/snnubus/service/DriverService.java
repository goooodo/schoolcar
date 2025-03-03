package com.snnubus.service;

import com.snnubus.entity.Driver;


import java.util.List;
//接口类，定义操作数据库的方法
public interface DriverService {
    public List<Driver> getAll();
    public List<Driver> getAllinfo();
    public int insertId(String driverId,String driverName,String driverSchool);



    public List<Driver> getById(String openid,String gschool);
    public List<Driver> getinfo(String openid,String gschool);
    public int updateinfo(String driverName,String driverSex,String driverTelephone,String openid,String gschool);
    public int updateagree(Integer driverAgree,String openid,String gschool);
    public int updatelatitude(String driverlatitude,String openid,String gschool);
    public int updatelongitude(String driverlongitude,String openid,String gschool);

}
