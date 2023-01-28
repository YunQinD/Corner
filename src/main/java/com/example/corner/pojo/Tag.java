package com.example.corner.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Time : 2023/1/24-23:51
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Data
@Component
public class Tag {
    private Integer id;
    private String content;
    private Integer clickRate;
    private String category;
    private String creatorId;
    private Date createdAt;
}
