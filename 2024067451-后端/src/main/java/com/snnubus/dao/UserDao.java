package com.snnubus.dao;

import com.snnubus.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository

public interface UserDao {
    /**
     * 获取乘客所有信息
     * @return
     */
    @Select("select user_id as userId, user_latitude as userLatitude, user_longitude as userLongitude from user")
    public List<User> getAll();

    /**
     * 用户注册
     * @param userId,userName,userSchool
     * @return
     */
    @Insert("INSERT INTO user (user_id, user_name, user_school) VALUES (#{userId}, #{userName}, #{userSchool})")
    public int insertId(String userId,String userName,String userSchool);

    /**
     * 用户信息更新
     * @param userName,userSex,userTelephone
     * @return
     */
    @Update("update user set user_name = #{userName},user_sex = #{userSex}, user_telephone = #{userTelephone} where user_id = #{openid} and user_school = #{gschool}")
    public int updateinfo(String userName,String userSex,String userTelephone,String openid,String gschool);


    /**
     * 查看指定乘客
     * @param openid
     * @return
     */
    @Select("select user_id as userId, user_name as userName, user_latitude as userLatitude, user_longitude as userLongitude from user where user_id = #{openid} and user_school = #{gschool}")
    public List<User> getById(String openid,String gschool);
    /**
     * 查看指定乘客
     * @param openid,gschool
     * @return
     */
    @Select("select user_name as userName, user_sex as userSex, user_telephone as userTelephone, user_avatar as userAvatar from user where user_id = #{openid} and user_school = #{gschool}")
    public List<User> getinfo(String openid,String gschool);
}
