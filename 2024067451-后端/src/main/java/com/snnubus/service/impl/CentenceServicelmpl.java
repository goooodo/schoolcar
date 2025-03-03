package com.snnubus.service.impl;

import com.snnubus.dao.CentenceDao;

import com.snnubus.entity.Centence;

import com.snnubus.service.CentenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class CentenceServicelmpl implements CentenceService {
    @Autowired
    private CentenceDao centenceDao;
    @Override
    public List<Centence> getByTitle(String title, String gschool) {
        return centenceDao.getByTitle(title,gschool);
    }
    public int insertTitle(String title,String centence,String commentName,String commentAvatar,String commentIdentity,String time,String gschool){
        return centenceDao.insertTitle(title,centence,commentName,commentAvatar,commentIdentity,time,gschool);
    }
}
