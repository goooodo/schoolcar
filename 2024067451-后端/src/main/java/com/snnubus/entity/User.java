package com.snnubus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId; //乘客id
    private String userName; //乘客name
    private String userSchool; //乘客id
    private String userSex; //乘客sex
    private String userTelephone; //乘客telephone
    private String userAvatar;



    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
