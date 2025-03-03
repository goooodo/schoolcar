package com.snnubus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@MapperScan("com.snnubus.dao")
@EnableWebSocket
public class SnnubusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnnubusApplication.class, args);
    }

}
