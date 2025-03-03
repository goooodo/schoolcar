package com.snnubus.dao;
import com.snnubus.entity.Driver;
import com.snnubus.entity.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface NewsDao {
    /**
     * 查看指定司机
     * @param gschool
     * @return
     */
    @Select("select theam as theam, photo as photo, context as context, kind as kind, kind2 as kind2 from news where school = #{gschool}")
    public List<News> getBySchool(String gschool);
}
