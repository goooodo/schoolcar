package com.snnubus.config;

import com.snnubus.handler.MyWebSocketHandler;
import com.snnubus.service.DriverService;
import com.snnubus.service.SiteinfoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//websocket配置类
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    private final DriverService driverService;
    private final SiteinfoService siteinfoService;
    //司机与站点信息类
    public WebSocketConfig(DriverService driverService,SiteinfoService siteinfoService) {
        this.driverService = driverService;
        this.siteinfoService=siteinfoService;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(driverService, siteinfoService), "/websocket")//websocket连接需要对其进行处理，故此处导入
                .setAllowedOrigins("*"); // 允许来自任意域名的连接
    }


}
