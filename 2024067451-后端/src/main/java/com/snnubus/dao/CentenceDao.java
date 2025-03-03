package com.snnubus.dao;

import com.snnubus.entity.Centence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CentenceDao {
    /**
     * 获取司机所有信息
     * @return
     */
    @Select("select title as title,centence as centence,comment_name as commentName, comment_avatar as commentAvatar, comment_identity as commentIdentity,time as time,comment_school as gschool from centence where title = #{title} and comment_school = #{gschool}")
    public List<Centence> getByTitle(String title, String gschool);
    /**
     * 用户注册
     * @param title,centence,commentName,commentAvatar,commentIdentity,time
     * @return
     */
    @Insert("insert into centence(title,centence,comment_name,comment_avatar,comment_identity,time,comment_school) values(#{title},#{centence},#{commentName},#{commentAvatar},#{commentIdentity},#{time},#{gschool})")
    public int insertTitle(String title,String centence,String commentName,String commentAvatar,String commentIdentity,String time,String gschool);

}
