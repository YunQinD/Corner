package com.example.corner.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.Post;
import com.example.corner.uitls.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.Date;

/**
 * @Time : 2023/1/25-16:45
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface PostService extends IService<Post> {
    APIResponse addPost(String title, Integer teamNum, Date goalTime, String content, MultipartFile picture, HttpSession session);
    APIResponse getAllPost(Integer pageNum, Integer pageSize);
    APIResponse getMyPost(Integer pageNum, Integer pageSize, HttpSession session);
    APIResponse getPostByCreatorId(Integer pageNum, Integer pageSize, String creatorId);
    APIResponse getPostByCategory(Integer pageNum, Integer pageSize, String category);
    APIResponse getPostByGoalTime(Integer pageNum, Integer pageSize, Date beginTime, Date endTime);
    APIResponse getPostByTitleAndContent(Integer pageNum, Integer pageSize, String content);
    APIResponse getPostByTeamNum(Integer pageNum, Integer pageSize, Integer teamNum);
    APIResponse deletePost(Integer postId, HttpSession session);

}
