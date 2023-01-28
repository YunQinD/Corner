package com.example.corner.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * @Time : 2023/1/18-19:45
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *user实体类*
 */

@Data
public class User {
    private Integer id;
    private String uid;
    private String avatarUrl;
    private String pwd;
    private String nickname;
    /**用户状态*/
    private Integer status;
    private String realName;
    private String gender;
    private String campus;
    private String major;
    private String grade;
    private Integer level;
    private Integer role;
    private Date createdAt;
    private Date updatedAt;

    public User(String uid, String pwd, String nickname, String realName, String gender, String campus, String major, String grade, Date createdAt, Date updatedAt) {
        this.uid = uid;
        this.pwd = pwd;
        this.nickname = nickname;
        this.realName = realName;
        this.gender = gender;
        this.campus = campus;
        this.major = major;
        this.grade = grade;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
