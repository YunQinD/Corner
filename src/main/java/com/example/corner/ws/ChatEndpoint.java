package com.example.corner.ws;

import com.example.corner.pojo.Message;
import com.example.corner.pojo.User;
import com.example.corner.service.ChatMessageService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.example.corner.uitls.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Time : 2023/1/8-7:27
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *聊天室服务器端*
 */
@Component
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatEndpoint {

    /**用来存储每一个客户端对象对应的ChatEndpoint对象*/
    private static Map<String, ChatEndpoint> onlineUsers= new ConcurrentHashMap<>();

    /**声明session对象，通过该对象将信息发送到指定用户*/
    private Session session;

    /**声明HttpSession对象，之前在HttpSession中存储了用户名*/
    private HttpSession httpSession;

    /**获取在线人姓名*/
    private Set<String> getOnLineIds() {
        return onlineUsers.keySet();
    }

    /**bean注入*/
    private ChatMessageService chatMessageService;

    /**
     * @Description: 向特定用户集广播消息
     * @Date: 2023/1/8
     * @Param names: Set集合
     * @Param message:
     * @return: com.example.corner.uitls.APIResponse
     **/
    private APIResponse broadcast(String uid, Set<String> ids, String message, Boolean isSystem) {
        try {
            for (String id : ids) {
                ChatEndpoint chatEndpoint = onlineUsers.get(id);
                chatEndpoint.session.getBasicRemote().sendText(message);
                if (isSystem){
                    chatMessageService.saveMessage(1,message,"system",id);
                }
                chatMessageService.saveMessage(0,message,uid,id);
            }
            return APIResponse.success("Send Successfully");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.MESSAGE_SEND_ERROR);
        }

    }

//    private APIResponse saveMessage(Boolean isSystem,String message,){
//        if (isSystem){
//            chatMessageService.saveMessage(1,message,"system",)
//        }
//    }

    /**
     * @Description: 连接建立时调用
     * @Date: 2023/1/8
     **/
    @OnOpen
    public APIResponse onOpen(Session session, EndpointConfig config) {
        try {
            //将形参session进行赋值
            this.session = session;
            //获取HttpSession对象
            HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
            this.httpSession = httpSession;
            //从HttpSession中获取当前用户名
            User user = (User) httpSession.getAttribute("user");
            String uid = user.getUid();
            //将该对象存储在容器中
            onlineUsers.put(uid, this);
            //将当前在线的所有的用户名推送到所有的客户端
            //1、获取消息
            String message = MessageUtils.getMessage(true, null, getOnLineIds());
            //2、调用方法进行消息推送并存储消息
            return broadcast(uid,getOnLineIds(),message,true);
        } catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    /**
     * @Description: 接收到客户端的消息时调用
     * @Date: 2023/1/8
     * @Param message: 从前端接受json格式的字符串，包含toName和message
     * @Param session:
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        try{
            //将message转化为message对象
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            //获取接收用户,生成用户集合
            String toName = mess.getToName();
            Set<String> names = new HashSet<>();
            names.add(toName);
            //获取消息数据
            String data = mess.getMessage();
            //获取当前登陆用户
            User user = (User) httpSession.getAttribute("user");
            String uid = user.getUid();
            //获取加工后的信息
            String resultMessage = MessageUtils.getMessage(false, uid, data);
            //发送数据
            broadcast(uid, names, resultMessage,false);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * @Description: 连接关闭时调用
     * @Date: 2023/1/8
     **/
    @OnClose
    public APIResponse onClose(Session session) {
        try {
            //从容器中删除指定的用户
            User user = (User) httpSession.getAttribute("user");
            String uid = user.getUid();
            onlineUsers.remove(uid);
            //生成消息
            String message = MessageUtils.getMessage(true, null, getOnLineIds());
            //广播
            return broadcast(uid,getOnLineIds(), message,true);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }

    }
}
