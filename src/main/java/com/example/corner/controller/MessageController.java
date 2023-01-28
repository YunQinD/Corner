package com.example.corner.controller;

import com.example.corner.service.ChatMessageService;
import com.example.corner.uitls.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.Date;

/**
 * @Time : 2023/1/21-10:44
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    private ChatMessageService chatMessageService;

    @Autowired
    public MessageController(ChatMessageService chatMessageService){
        this.chatMessageService = chatMessageService;
    }

    /**保存消息*/
    @PostMapping("/save")
    public APIResponse saveUserMessage(String peerId, String message, HttpSession session){
        return chatMessageService.saveMessage(0, message, peerId, session);
    }

    /**删除消息*/
    @PostMapping("/delete")
    public APIResponse deleteUserMessage(String message, String peerId, HttpSession session, Date sendAt){
        return chatMessageService.deleteMessage(message, peerId, session, sendAt);
    }

    /**发信人撤回消息*/
    @PostMapping("/withdraw")
    public APIResponse withdrawUserMessage(String message, String peerId, HttpSession session, Date sendAt){
        return chatMessageService.withdrawMessage(message, peerId, session, sendAt);
    }

    /**获取所有可见消息*/
    @GetMapping
    public APIResponse getAllVisibleMessage(Integer pageNum, Integer pageSize, String hostId, String peerId){
        return chatMessageService.getVisibleMessage(pageNum, pageSize, hostId, peerId);
    }
}
