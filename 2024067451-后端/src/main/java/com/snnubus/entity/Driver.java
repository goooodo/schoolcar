package com.snnubus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 自动生成getter和setter方法，以及equals、hashCode和toString方法
@AllArgsConstructor // 自动生成全参构造方法
@NoArgsConstructor // 自动生成无参构造方法
public class Driver {
    private String driverId; // 司机ID
    private String driverLatitude; // 司机所在位置的纬度
    private String driverLongitude; // 司机所在位置的经度
    private String driverName;
    private String driverSchool;
    private String driverSex;
    private String driverTelephone;
    private String driverAvatar;
    private Integer driverAgree;

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", driverLatitude='" + driverLatitude + '\'' +
                ", driverLongitude='" + driverLongitude + '\'' +
                ", driverSchool='" + driverSchool + '\'' +
                '}';
    }
}