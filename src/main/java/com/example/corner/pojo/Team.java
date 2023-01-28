package com.example.corner.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Time : 2023/1/25-18:05
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Data
@Component
public class Team {
    private Integer id;
    private Integer postId;
    private String leaderId;
    private Integer teamNum;
    private String memberId;
    private Date createdAt;
    private Date joinAt;
}
