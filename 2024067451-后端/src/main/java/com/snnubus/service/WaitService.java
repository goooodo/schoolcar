package com.snnubus.service;

import com.snnubus.entity.Waitcar;

import java.util.List;

public interface WaitService {
    public List<Waitcar> getAll(String waitId);
    public int insertId(String waitId);
    public int updatewaitcar(String waitState,String waitId);
}
