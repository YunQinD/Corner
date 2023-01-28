package com.example.corner.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Time : 2023/1/26-21:54
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *申请缓冲实体类*
 */
@Data
@Component
public class ApplyBuffer {
    private Integer id;
    private String applyId;
    private Integer tid;
    private Date applyAt;
    private Integer status;
    private Date solveAt;
}
