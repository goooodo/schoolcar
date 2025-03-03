package com.snnubus.service;

import com.snnubus.entity.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> getById(String openid,String gschool);
    public int insertId(String driverId,String driverComment,String commentName,String commentAvatar,String commentIdentity,String time,String gschool);
}
