package com.snnubus.service.impl;

import com.snnubus.dao.WaitDao;
import com.snnubus.entity.Waitcar;
import com.snnubus.service.WaitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitServiceImpl implements WaitService {
    @Autowired
    private WaitDao waitDao;
    @Override
    public List<Waitcar> getAll(String waitId)
    {return waitDao.getAll(waitId);}
    @Override
    public int insertId(String waitId)
    {return waitDao.insertId(waitId);}
    @Override
    public int updatewaitcar(String waitState,String waitId)
    {return waitDao.updatewaitcar(waitState,waitId);}

}
