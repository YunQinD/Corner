package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.User;
import com.example.corner.uitls.APIResponse;

import javax.servlet.http.HttpSession;

/**
 * @Time : 2023/1/19-22:58
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface UserService extends IService<User> {
    User getUserByUid(String uid);
    APIResponse logOutUserWithPwdAndCode(String pwd, String code, HttpSession session);

}
