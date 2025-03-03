package com.snnubus.service;
import com.snnubus.entity.Driver;
import com.snnubus.entity.News;


import java.util.List;
//接口类，定义操作数据库的方法
public interface NewsService {
    public List<News> getBySchool(String gschool);
}
