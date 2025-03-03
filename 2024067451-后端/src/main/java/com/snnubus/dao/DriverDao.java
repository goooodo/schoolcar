package com.snnubus.dao;

import com.snnubus.entity.Driver;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface DriverDao {//访问司机
    /**
     * 获取司机所有信息
     * @return
     */
    @Select("select driver_id as driverId, driver_latitude as driverLatitude, driver_longitude as driverLongitude, driver_school as driverSchool from driver")
    public List<Driver> getAll();
    /**
     * 获取司机所有信息
     * @return
     */
    @Select("select driver_id as driverId,drive_name as driverName, drive_sex as driverSex, drive_telephone as driverTelephone, drive_avatar as driverAvatar,driver_school as driverSchool,drive_agree as driverAgree from driver")
    public List<Driver> getAllinfo();
    /**
     * 用户注册
     * @param driverId,driverName,driverSchool
     * @return
     */
    @Insert("insert into driver(driver_id,drive_name,driver_school) values(#{driverId},#{driverName},#{driverSchool})")
    public int insertId(String driverId,String driverName,String driverSchool);



    /**
     * 查看指定司机
     * @param openid
     * @return
     */
    @Select("select driver_id as driverId, drive_name as driverName, driver_latitude as driverLatitude, driver_longitude as driverLongitude from driver where driver_id = #{openid} and driver_school = #{gschool}")
    public List<Driver> getById(String openid,String gschool);

    /**
     * 查看指定司机
     * @param openid,gschool
     * @return
     */
    @Select("select drive_name as driverName, drive_sex as driverSex, drive_telephone as driverTelephone, drive_avatar as driverAvatar,drive_agree as driverAgree from driver where driver_id = #{openid} and driver_school = #{gschool}")
    public List<Driver> getinfo(String openid,String gschool);

    /**
     * 司机信息更新
     * @param driverName,driverSex,driverTelephone
     * @return
     */
    @Update("update driver set drive_name = #{driverName},drive_sex = #{driverSex}, drive_telephone = #{driverTelephone} where driver_id = #{openid} and driver_school = #{gschool}")
    public int updateinfo(String driverName,String driverSex,String driverTelephone,String openid,String gschool);

    /**
     * 纬度
     * @param driverAgree
     * @return
     */
    @Update("update driver set drive_agree = #{driverAgree} where driver_id = #{openid} and driver_school = #{gschool}")
    public int updateagree(Integer driverAgree,String openid,String gschool);
    /**
     * 纬度
     * @param driverLatitude
     * @return
     */
    @Update("update driver set driver_latitude = #{driverLatitude} where driver_id = #{openid} and driver_school = #{gschool}")
    public int updatelatitude(String driverLatitude,String openid,String gschool);

    /**
     * 经度
     * @param driverLongitude
     * @return
     */
    @Update("update driver set driver_longitude = #{driverLongitude} where driver_id = #{openid} and driver_school = #{gschool}")
    public int updatelongitude(String driverLongitude,String openid,String gschool);


}
