package org.example.iotbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI; // 这个 import 现在不是必需的了，但留着也没关系
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketIntegrationTest.class);

    @LocalServerPort
    private int port;

    private String serverUrl;

    private BlockingQueue<String> receivedMessages;

    @BeforeEach
    public void setup() {
        this.serverUrl = "ws://localhost:" + port + "/ws/data";
        this.receivedMessages = new LinkedBlockingDeque<>();
    }

    @Test
    public void testWebSocketConnectionAndEcho() throws Exception {
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();

        TextWebSocketHandler clientHandler = new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                logger.info("Client: Connection established.");
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) {
                logger.info("Client: Received message - '{}'", message.getPayload());
                receivedMessages.add(message.getPayload());
            }
        };

        logger.info("Client: Connecting to {}", serverUrl);

        // ---  这里是修改的关键点 ---
        // 将 new URI(serverUrl) 直接替换为 serverUrl 字符串
        WebSocketSession session = webSocketClient.doHandshake(clientHandler, serverUrl).get();

        assertNotNull(session, "Session should not be null.");

        String welcomeMessage = receivedMessages.poll(2, TimeUnit.SECONDS);
        assertEquals("Connection established with Spring Boot server!", welcomeMessage, "Welcome message is incorrect.");

        String testMessage = "Hello from Test Client!";
        logger.info("Client: Sending message - '{}'", testMessage);
        session.sendMessage(new TextMessage(testMessage));

        String echoMessage = receivedMessages.poll(2, TimeUnit.SECONDS);
        assertEquals("Server received: " + testMessage, echoMessage, "Echo message is incorrect.");

        session.close();
    }
}