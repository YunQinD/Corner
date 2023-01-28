package com.example.corner.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Time : 2023/1/18-20:40
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *反馈的实体类*
 */
@Data
@Component
public class FeedBack {
    private Integer id;
    private String uid;
    private String content;
    private String reply;
    private String status;
    private Date createdAt;
    private Date replyAt;
}
