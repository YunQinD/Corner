package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.FeedBack;
import com.example.corner.uitls.APIResponse;

import javax.servlet.http.HttpSession;

/**
 * @Time : 2023/1/18-20:57
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface FeedBackService extends IService<FeedBack> {
    /**保存反馈*/
    APIResponse saveFeedBack(String content, HttpSession session);
}
