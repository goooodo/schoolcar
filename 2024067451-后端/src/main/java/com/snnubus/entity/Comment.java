package com.snnubus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 自动生成getter和setter方法，以及equals、hashCode和toString方法
@AllArgsConstructor // 自动生成全参构造方法
@NoArgsConstructor // 自动生成无参构造方法
public class Comment {
    private String driverId;
    private String driverComment;
    private String commentName;
    private String commentAvatar;
    private String commentIdentity;
    private String time;
    private String gschool;

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", driverComment='" + driverComment + '\'' +
                ", commentName='" + commentName + '\'' +
                '}';
    }
}
