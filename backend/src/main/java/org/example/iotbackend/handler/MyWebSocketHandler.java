package org.example.iotbackend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component // 将这个处理器注册为Spring Bean
public class MyWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    /**
     * 当WebSocket连接建立后调用
     * @param session 当前连接的会话
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 打印会话ID，确认连接成功
        logger.info("New connection established: {}", session.getId());
        // 向客户端发送一条欢迎消息
        session.sendMessage(new TextMessage("Connection established with Spring Boot server!"));
    }

    /**
     * 当收到客户端发来的文本消息时调用
     * @param session 当前连接的会话
     * @param message 收到的消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("Received message from {}: {}", session.getId(), payload);

        // 简单地将收到的消息原样返回给客户端（回声测试）
        session.sendMessage(new TextMessage("Server received: " + payload));
    }

    /**
     * 当WebSocket连接关闭后调用
     * @param session 当前连接的会话
     * @param status 关闭状态
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Connection closed: {} with status: {}", session.getId(), status);
    }

    /**
     * 当发生传输错误时调用
     * @param session 当前连接的会话
     * @param exception 发生的异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Transport error for session {}: {}", session.getId(), exception.getMessage());
    }
}