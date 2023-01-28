package com.example.corner.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Time : 2023/1/21-10:55
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Data
@Component
public class ChatMessage {
    private String hostId;
    private String peerId;
    private Integer messageType;
    private String content;
    private Integer hostStatus;
    private Integer peerStatus;
    private Date sendAt;
    private Date updatedAt;
}
