package org.example.iotbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.example.iotbackend.handler.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocket // 启用WebSocket支持
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyWebSocketHandler myWebSocketHandler; // 注入我们稍后创建的处理器

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册一个WebSocket端点
        // "/ws/data" 是硬件或前端需要连接的地址
        // .setAllowedOrigins("*") 允许所有来源的跨域连接，方便测试
        registry.addHandler(myWebSocketHandler, "/ws/data")
                .setAllowedOrigins("*");
    }
}