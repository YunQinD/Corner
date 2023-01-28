package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.Tag;
import com.example.corner.uitls.APIResponse;

import javax.servlet.http.HttpSession;

/**
 * @Time : 2023/1/24-23:54
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface TagService extends IService<Tag> {
    APIResponse addTag(String content, String category, HttpSession session);
    APIResponse getAllTags(Integer pageNum, Integer pageSize);
    APIResponse getTagsByCategory(Integer pageNum, Integer pageSize, String category);
    APIResponse getMyTags(HttpSession session);
    APIResponse getContentLikeTags(Integer pageNum, Integer pageSize, String content);
}
