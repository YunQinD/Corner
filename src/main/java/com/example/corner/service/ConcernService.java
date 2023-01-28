package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.Concern;
import com.example.corner.uitls.APIResponse;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @Time : 2023/1/20-23:20
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface ConcernService extends IService<Concern> {
    APIResponse addUserConcernByUid(String friendId, HttpSession session);
    APIResponse deleteUserConcernByUid(String friendId, HttpSession session);
    APIResponse getAllUSerConcern(Integer pageNum, Integer pageSize, HttpSession session);
    APIResponse addPostConcernByUid(Integer pid, HttpSession session);
    APIResponse deletePostConcernByUid(Integer pid, HttpSession session);
    APIResponse getAllPostConcern(Integer pageNum, Integer pageSize, HttpSession session);

}
