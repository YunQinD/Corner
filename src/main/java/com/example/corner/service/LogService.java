package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.User;
import com.example.corner.uitls.APIResponse;

/**
 * @Time : 2023/1/7-13:07
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *登陆service层*
 */
public interface LogService extends IService<User> {

    APIResponse loginWithAccountAndPwd(String uid, String pwd);
    APIResponse addUserMainAccount(String uid, String pwd, String nickname);
}
