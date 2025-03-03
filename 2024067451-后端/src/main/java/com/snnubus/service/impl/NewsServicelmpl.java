package com.snnubus.service.impl;

import com.snnubus.dao.NewsDao;
import com.snnubus.entity.News;
import com.snnubus.service.DriverService;
import com.snnubus.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//DriverService类的实例
@Service
public class NewsServicelmpl implements NewsService {
    @Autowired
    private NewsDao newsDao;
    @Override
    public List<News> getBySchool(String gschool) {
        return newsDao.getBySchool(gschool);
    }
}
