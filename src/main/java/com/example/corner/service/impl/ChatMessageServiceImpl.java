package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.ChatMessageMapper;
import com.example.corner.pojo.ChatMessage;
import com.example.corner.pojo.User;
import com.example.corner.service.ChatMessageService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Time : 2023/1/21-11:02
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService{

    private ReentrantLock lock = new ReentrantLock();
    private final ChatMessageMapper chatMessageMapper;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageMapper chatMessageMapper){
        this.chatMessageMapper = chatMessageMapper;
    }

    /**
     * @Description: saveMessage方法，讲消息存储在数据库中
     * @Date: 2023/1/22
     * @Param messageType: 数据类型，0为私聊，1为系统广播
     * @Param content: 消息内容
     * @Param peerId: 收件人Id
     * @Param session: session
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse saveMessage(Integer messageType, String content, String peerId, HttpSession session) {
        lock.lock();
        try {
            User user = (User) session.getAttribute("user");
            String hostId = user.getUid();
            Date date = new Date(System.currentTimeMillis());
            if (messageType == 1) {
                chatMessageMapper.addPrivateChatMessage(messageType, "system", peerId, content, date, date);
                return APIResponse.success("系统消息储存成功");
            }
            if (messageType == 0 && user.getUid() == null) {
                return APIResponse.error(ErrorCode.MESSAGE_SERVICE_ERROR, "没有指定发件人");
            }
            if (chatMessageMapper.addPrivateChatMessage(messageType, hostId, peerId, content, date, date)) {
                return APIResponse.success("用户消息存储成功");
            }
            return APIResponse.error(ErrorCode.MESSAGE_SERVICE_ERROR,"消息保存出错");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"saveMessage;[ChatMessageServiceImpl]");
        }finally {
            lock.unlock();
        }
    }

    /**
     * @Description: saveMessage方法重写，为了方便其他地方使用
     * @Date: 2023/1/22
     * @Param hostId:发送人姓名
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse saveMessage(Integer messageType, String message, String hostId, String peerId) {
        lock.lock();
        try {
            Date date = new Date(System.currentTimeMillis());
            if (messageType == 1) {
                chatMessageMapper.addPrivateChatMessage(messageType, "system", peerId, message, date, date);
                return APIResponse.success("系统消息储存成功");
            }
            if (chatMessageMapper.addPrivateChatMessage(messageType, hostId, peerId, message, date, date)) {
                return APIResponse.success("用户消息存储成功");
            }
            return APIResponse.error(ErrorCode.MESSAGE_SERVICE_ERROR,"消息存储出错");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"saveMessage;[ChatMessageServiceImpl]");
        }finally {
            lock.unlock();
        }
    }

    /**
     * @Description: 删除消息（软删除，在删除方看不见）
     * @Date: 2023/1/23
     * @Param message: 消息，需与发送格式一致
     * @Param session: session
     * @Param sendAt: 发送时间，定位信息唯一性
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse deleteMessage(String message, String peerId, HttpSession session, Date sendAt) {
        lock.lock();
        try {
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            ChatMessage chatMessage = chatMessageMapper.getChatMessageByMessageAndDate(message, sendAt, uid, peerId);
            Date date = new Date(System.currentTimeMillis());
            if (chatMessage == null || uid == null){
                return APIResponse.error(ErrorCode.NO_SUCH_MESSAGE,"没有对应消息或没有对应用户");
            }
            if (uid.equals(chatMessage.getHostId())){
                if (chatMessageMapper.deleteHostMessageByMessageAndDate(date, message, sendAt, uid, peerId)){
                    return APIResponse.success("己方消息删除成功");
                }
            }
            if (uid.equals(chatMessage.getPeerId())){
                if (chatMessageMapper.deletePeerMessageByMessageAndDate(date, message, sendAt, uid, peerId)){
                    return APIResponse.success("对方消息删除成功");
                }
            }
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"消息删除失败");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"deleteMessage;[ChatMessageServiceImpl]");
        }finally {
            lock.unlock();
        }
    }

    /**
     * @Description: 发信人撤回消息
     * @Date: 2023/1/24
     * @Param message: 消息
     * @Param peerId: 收信人ID
     * @Param session: session
     * @Param sendAt: 发信时间
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse withdrawMessage(String message, String peerId, HttpSession session, Date sendAt) {
        lock.lock();
        try {
            Date date = new Date(System.currentTimeMillis());
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            ChatMessage chatMessage = chatMessageMapper.getChatMessageByMessageAndDate(message, sendAt, uid, peerId);
            if (chatMessage == null || uid == null){
                return APIResponse.error(ErrorCode.NO_SUCH_MESSAGE,"没有对应消息或没有对应用户");
            }
            if (uid.equals(chatMessage.getHostId())){
                if (chatMessageMapper.withdrawMessageByMessageAndDate(date, message, sendAt, uid, peerId)){
                    return APIResponse.success("消息撤回成功");
                }
                return APIResponse.success("消息撤回失败");
            }
            return APIResponse.error(ErrorCode.MESSAGE_SERVICE_ERROR,"消息撤回失误，并非是您发送的消息");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"withdrawMessage;[ChatMessageServiceImpl]");
        }finally {
            lock.unlock();
        }

    }

    /**
     * @Description: 获取可见消息
     * @Date: 2023/1/24
     * @Param hostId: 发信人
     * @Param peerId: 收信人
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getVisibleMessage(Integer pageNum, Integer pageSize, String hostId, String peerId) {
        lock.lock();
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<ChatMessage> chatMessages = chatMessageMapper.getVisibleMessage(hostId, peerId);
            PageInfo<ChatMessage> chatMessagePageInfo = new PageInfo<ChatMessage>(chatMessages);
            return APIResponse.success(chatMessagePageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"getVisibleMessage;[ChatMessageServiceImpl]");
        }finally {
            lock.unlock();
        }

    }
}
