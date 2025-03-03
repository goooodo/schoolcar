package com.snnubus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Siteinfo {
    private String siteinfoName; //站点名称
    private String siteinfoLatitude; //站点纬度
    private String siteinfoLongitude; //站点经度
    private String siteinfoCount; //站点人数数量
    private String siteinfoSchool; //站点学校

    @Override
    public String toString() {
        return "Siteinfo{" +
                "siteinfoName='" + siteinfoName + '\'' +
                ", siteinfoLatitude='" + siteinfoLatitude + '\'' +
                ", siteinfoLongitude='" + siteinfoLongitude + '\'' +
                ", siteinfoCount='" + siteinfoCount + '\'' +
                ", siteinfoSchool='" + siteinfoSchool + '\'' +
                '}';
    }
}
