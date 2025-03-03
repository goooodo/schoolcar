package com.snnubus.dao;

import com.snnubus.entity.Waitcar;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface WaitDao {
    /**
     * 获取用户所有信息
     * @param waitId
     * @return
     */
    @Select("select wait_id as waitId, wait_state as waitState from waitcar where wait_id = #{waitId}")
    public List<Waitcar> getAll(String waitId);
    /**
     * 用户注册
     * @param waitId
     * @return
     */
    @Insert("insert into waitcar(wait_id) values(#{waitId})")
    public int insertId(String waitId);
    /**
     * 纬度
     * @param waitState
     * @return
     */
    @Update("update waitcar set wait_state = #{waitState} where wait_id = #{waitId}")
    public int updatewaitcar(String waitState,String waitId);
}
