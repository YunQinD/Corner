package com.example.corner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Time : 2023/1/8-7:37
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *websocket配置类*
 */
@Configuration
public class WebSocketConfig {

    /**
     * @Description: 注入ServerEndpointExporter bean对象，自动注册使用了@ServerEndpoint的bean
     * @Date: 2023/1/8
     **/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
