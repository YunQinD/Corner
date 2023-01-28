package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.ChatMessage;
import com.example.corner.uitls.APIResponse;

import javax.servlet.http.HttpSession;
import java.sql.Date;

/**
 * @Time : 2023/1/21-11:02
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface ChatMessageService extends IService<ChatMessage> {
    APIResponse saveMessage(Integer messageType, String content, String peerId, HttpSession session);
    APIResponse saveMessage(Integer messageType, String content, String hostId, String peerId);
    APIResponse deleteMessage(String message, String peerId, HttpSession session, Date sendAt);
    APIResponse withdrawMessage(String message, String peerId, HttpSession session, Date sendAt);
    APIResponse getVisibleMessage(Integer pageNum, Integer pageSize, String hostId, String peerId);
}
