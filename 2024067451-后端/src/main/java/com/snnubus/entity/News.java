package com.snnubus.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private String theam; // 题目
    private String context; // 正文
    private String photo; // 图片
    private String kind;
    private String kind2;
    private String school;

    @Override
    public String toString() {
        return "News{" +
                "theam='" + theam + '\'' +
                ", context='" + context + '\'' +
                ", kind='" + kind + '\'' +
                ", kind1='" + kind2 + '\'' +
                '}';
    }
}
