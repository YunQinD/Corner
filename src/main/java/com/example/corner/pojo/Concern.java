package com.example.corner.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Time : 2023/1/20-22:58
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Data
@Component
public class Concern {
    private String uid;
    private String friendId;
    private Integer pid;
    private Date createdAt;
    private Integer isDeleted;
    private Date updatedAt;
}
