package com.example.corner.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Time : 2023/1/25-16:30
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */

@Data
@Component
public class Post {
    private Integer id;
    private String creatorId;
    private String title;
    private Integer teamNum;
    private Date goalTime;
    private String thumbnailUrl;
    private Date createdAt;
    private Date updatedAt;
    private Integer isDeleted;
    private String pictureUrl;
    private String content;
}
