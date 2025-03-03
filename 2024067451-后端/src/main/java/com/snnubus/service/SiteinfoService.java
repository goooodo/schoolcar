package com.snnubus.service;


import com.snnubus.entity.Siteinfo;

import java.util.List;

public interface SiteinfoService {
    public List<Siteinfo> getAll();
    public int updatecount(String siteinfoCount,String siteinfoName);
}
