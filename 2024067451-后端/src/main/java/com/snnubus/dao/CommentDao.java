package com.snnubus.dao;

import com.snnubus.entity.Comment;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentDao {
    /**
     * 获取司机所有信息
     * @return
     */
    @Select("select driver_id as driverId,driver_comment as driverComment,comment_name as commentName, comment_avatar as commentAvatar, comment_identity as commentIdentity,time as time,comment_school as gschool from comment where driver_id = #{openid} and comment_school = #{gschool}")
    public List<Comment> getById(String openid,String gschool);
    /**
     * 用户注册
     * @param driverId,driverComment,commentName,commentAvatar,commentIdentity,time
     * @return
     */
    @Insert("insert into comment(driver_id,driver_comment,comment_name,comment_avatar,comment_identity,time,comment_school) values(#{driverId},#{driverComment},#{commentName},#{commentAvatar},#{commentIdentity},#{time},#{gschool})")
    public int insertId(String driverId,String driverComment,String commentName,String commentAvatar,String commentIdentity,String time,String gschool);
}
