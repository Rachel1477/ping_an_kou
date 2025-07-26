package org.example.iotbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这是 Spring Boot 应用的主启动类。
 * 运行这个类的 main 方法，整个后端服务就会启动。
 */
@SpringBootApplication // 这是一个复合注解，包含了@Configuration, @EnableAutoConfiguration, @ComponentScan
public class IotBackendApplication {

    /**
     * Java程序的入口方法。
     * SpringApplication.run() 会负责启动嵌入式的Web服务器（如Tomcat），
     * 并加载所有的Spring配置和Bean。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(IotBackendApplication.class, args);

        System.out.println("==================================================");
        System.out.println("=                                                =");
        System.out.println("=      IoT Backend Server has started!           =");
        System.out.println("=      WebSocket Endpoint: ws://localhost:8080/ws/data  =");
        System.out.println("=      Test Page: http://localhost:8080/           =");
        System.out.println("=                                                =");
        System.out.println("==================================================");

    }

}