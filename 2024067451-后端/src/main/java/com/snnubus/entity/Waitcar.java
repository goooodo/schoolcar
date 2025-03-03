package com.snnubus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Waitcar {
    private String waitId; //等车人的id
    private String waitState; //等车人的状态（判断其是否已经进入候车队列）


    @Override
    public String toString() {
        return "Waitcar{" +
                "waitid='" + waitId + '\'' +
                ", waitstate='" + waitState + '\'' +
                '}';
    }
}
