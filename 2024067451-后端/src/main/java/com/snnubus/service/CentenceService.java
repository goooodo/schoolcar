package com.snnubus.service;

import com.snnubus.entity.Centence;


import java.util.List;

public interface CentenceService {
    public List<Centence> getByTitle(String title, String gschool);
    public int insertTitle(String title,String centence,String commentName,String commentAvatar,String commentIdentity,String time,String gschool);

}
