package com.snnubus.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snnubus.entity.Driver;
import com.snnubus.entity.Siteinfo;
import com.snnubus.service.DriverService;
import com.snnubus.service.SiteinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> sessions = new HashMap<>(); // 存储连接ID和WebSocketSession的映射关系
    private final DriverService driverService; // 司机类
    private final SiteinfoService siteinfoService; // 站点信息信息类
    private ThreadPoolTaskScheduler taskScheduler; // 线程池任务调度器
    private ScheduledFuture<?> scheduledFuture; // 定时任务Future
    private ScheduledFuture<?> scheduledFuture2; // 另一个定时任务Future

    @Autowired
    public MyWebSocketHandler(DriverService driverService, SiteinfoService siteinfoService) {
        this.driverService = driverService;
        this.siteinfoService = siteinfoService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(session.getUri()).build();
        MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
        String connectionId = queryParams.getFirst("connectionId"); // 连接ID
        String userId = queryParams.getFirst("userId"); // 用户ID

        sessions.put(connectionId + "-" + userId, session); // 将连接ID和WebSocketSession存入映射关系中
        System.out.println(sessions);
        System.out.println(connectionId);
        System.out.println("连接建立");
    }

    // 启动定时任务，周期性从数据库获取信息并发送给连接1
    private void startSendingDatabaseInfo() {
        scheduledFuture = taskScheduler.scheduleAtFixedRate(() -> {
            try {
                // 创建 ObjectMapper 对象，用于将对象转换为 JSON 字符串
                ObjectMapper objectMapper = new ObjectMapper();
                // 从数据库获取司机列表
                List<Driver> drivers = driverService.getAll();
                // 将司机信息列表转换为 JSON 字符串
                String json = objectMapper.writeValueAsString(drivers);
                // 创建 TextMessage 对象，用于向客户端发送消息
                TextMessage message = new TextMessage(json);
                // 遍历所有 WebSocketSession，发送消息给连接1
                for (WebSocketSession session : sessions.values()) {
                    UriComponents uriComponents = UriComponentsBuilder.fromUri(session.getUri()).build();
                    MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
                    String connectionId = queryParams.getFirst("connectionId");
                    String userId = queryParams.getFirst("userId");
                    if (connectionId.equals("connection1")) {
                        session.sendMessage(message);
                        System.out.println("定时发送给 connection1");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 5000); // 每5000毫秒执行一次任务
    }

    // 启动定时任务，周期性从数据库获取信息并发送给连接2
    private void startSendingDatabaseInfo2() {
        scheduledFuture2 = taskScheduler.scheduleAtFixedRate(() -> {
            try {
                // 创建 ObjectMapper 对象，用于将对象转换为 JSON 字符串
                ObjectMapper objectMapper = new ObjectMapper();
                // 从数据库获取站点信息列表
                List<Siteinfo> siteinfos = siteinfoService.getAll();
                // 将站点信息列表转换为 JSON 字符串
                String json = objectMapper.writeValueAsString(siteinfos);
                // 创建 TextMessage 对象，用于向客户端发送消息
                TextMessage message = new TextMessage(json);

                // 遍历所有 WebSocketSession，发送消息给连接2
                for (WebSocketSession session2 : sessions.values()) {
                    UriComponents uriComponents = UriComponentsBuilder.fromUri(session2.getUri()).build();
                    MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
                    String connectionId = queryParams.getFirst("connectionId");
                    String userId = queryParams.getFirst("userId");

                    if (connectionId.equals("connection2")) {
                        session2.sendMessage(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 5000); // 每5000毫秒执行一次任务
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(session.getUri()).build();
        MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
        String connectionId = queryParams.getFirst("connectionId");
        String userId = queryParams.getFirst("userId");
        System.out.println("处理消息时的session:" + session);

        if (connectionId.equals("connection1")) {//将司机位置发给前端
            System.out.println(message);
            // 处理连接1的消息
            System.out.println("Connection 1: Received message: " + message);

            // 启动定时任务，周期性发送数据库信息给连接1
            taskScheduler = new ThreadPoolTaskScheduler();
            taskScheduler.initialize();
            startSendingDatabaseInfo();
        } else if (connectionId.equals("connection2")) {//将站点信息数据库的信息发给前端
            // 处理连接2的消息
            System.out.println("Connection 2: Received message: " + message);
            // 启动定时任务，周期性发送数据库信息给连接2
            taskScheduler = new ThreadPoolTaskScheduler();
            taskScheduler.initialize();
            startSendingDatabaseInfo2();
        } else if (connectionId.equals("connection3")) {//候车聊天室，给候车聊天室里的每一个websocket连接转发接收到的消息
            // 广播收到的消息给所有连接
            for (WebSocketSession broadcastSession : sessions.values()) {
                System.out.println(sessions.values());
                UriComponents uriComponents3 = UriComponentsBuilder.fromUri(broadcastSession.getUri()).build();//消息过滤
                MultiValueMap<String, String> queryParams3 = uriComponents3.getQueryParams();
                String connectionId3 = queryParams3.getFirst("connectionId");
                if (connectionId3.equals("connection3")) {
                    // 发送消息给连接3
                    System.out.println(message.getPayload());
                    String payload = message.getPayload();
                    // 广播消息给连接3
                    broadcastSession.sendMessage(new TextMessage(payload));
                }
            }
        }
    }

    //连接关闭
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(session.getUri()).build();
        MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
        String connectionId = queryParams.getFirst("connectionId");
        String userId = queryParams.getFirst("userId");

        System.out.println("连接关闭: " + connectionId + "-" + userId);
        sessions.remove(connectionId + "-" + userId);

        if (sessions.isEmpty()) {
            if (taskScheduler != null) {
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(true); // 取消定时任务
                } else if (scheduledFuture2 != null) {
                    scheduledFuture2.cancel(true); // 取消定时任务
                }
                taskScheduler.shutdown(); // 关闭调度器
                taskScheduler = null;
            }
        }
    }
}