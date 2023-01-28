package com.example.corner.ws;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @Time : 2023/1/8-8:45
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *将HttpSession存入EndpointConfig中*
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        /**将httpSession对象存储到配置对象中*/
        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}
