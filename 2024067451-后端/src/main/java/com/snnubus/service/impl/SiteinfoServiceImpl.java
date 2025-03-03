package com.snnubus.service.impl;

import com.snnubus.dao.SiteDao;
import com.snnubus.entity.Siteinfo;
import com.snnubus.service.SiteinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//实例
@Service
public class SiteinfoServiceImpl implements SiteinfoService {
    @Autowired
    private SiteDao siteDao;
    @Override
    public List<Siteinfo> getAll(){return siteDao.getAll();}
    @Override
    public int updatecount(String siteinfoCount,String siteinfoName){return siteDao.updatecount(siteinfoCount,siteinfoName);}
}
