package com.snnubus.service.impl;

import com.snnubus.dao.DriverDao;
import com.snnubus.entity.Driver;
import com.snnubus.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//DriverService类的实例
@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverDao driverDao;

    @Override
    public List<Driver> getAll() {
        return driverDao.getAll();
    }
    @Override
    public List<Driver> getAllinfo()
    {
        return driverDao.getAllinfo();
    }

    @Override
    public int insertId(String driverId,String driverName,String driverSchool) {
        return driverDao.insertId(driverId,driverName,driverSchool);
    }
    @Override
    public int updateinfo(String driverName,String driverSex,String driverTelephone,String openid,String gschool){
        return  driverDao.updateinfo(driverName,driverSex,driverTelephone,openid,gschool);
    }
    @Override
    public int updateagree(Integer driverAgree,String openid,String gschool){
        return  driverDao.updateagree(driverAgree,openid,gschool);
    }
    @Override
    public List<Driver> getById(String openid,String gschool) {
        return driverDao.getById(openid,gschool);
    }
    @Override
    public List<Driver> getinfo(String openid,String gschool) {
        return driverDao.getinfo(openid,gschool);
    }

    @Override
    public int updatelatitude(String driverLatitude, String openid,String gschool) {
        return driverDao.updatelatitude(driverLatitude, openid,gschool);
    }

    @Override
    public int updatelongitude(String driverLongitude, String openid,String gschool) {
        return driverDao.updatelongitude(driverLongitude, openid,gschool);
    }
}