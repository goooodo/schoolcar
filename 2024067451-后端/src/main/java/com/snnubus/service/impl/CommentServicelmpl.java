package com.snnubus.service.impl;

import com.snnubus.dao.CommentDao;
import com.snnubus.service.CommentService;
import com.snnubus.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServicelmpl implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Override
    public List<Comment> getById(String openid,String gschool) {
        return commentDao.getById(openid,gschool);
    }
    public int insertId(String driverId,String driverComment,String commentName,String commentAvatar,String commentIdentity,String time,String gschool){
        return commentDao.insertId(driverId,driverComment,commentName,commentAvatar,commentIdentity,time,gschool);
    }
}
