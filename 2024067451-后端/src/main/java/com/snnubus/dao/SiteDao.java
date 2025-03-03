package com.snnubus.dao;

import com.snnubus.entity.Siteinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SiteDao {
    /**
     * 获取所有站点信息
     * @return
     */
    @Select("select siteinfo_name as siteinfoName, siteinfo_latitude as siteinfoLatitude,siteinfo_longitude as siteinfoLongitude,siteinfo_count as siteinfoCount,siteinfo_school as siteinfoSchool from siteinfo")
    public List<Siteinfo> getAll();
    /**
     * 更新站点人数
     */
    @Update("update siteinfo set siteinfo_count = #{siteinfoCount} where siteinfo_name = #{siteinfoName}")
    public int updatecount(String siteinfoCount,String siteinfoName);
}
