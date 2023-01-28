package com.example.corner.controller;

import com.example.corner.pojo.Concern;
import com.example.corner.service.ConcernService;
import com.example.corner.service.UserService;
import com.example.corner.uitls.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Time : 2023/1/20-23:18
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *关注接口*
 */
@RestController
@RequestMapping("/concerns")
public class ConcernController {

    private final UserService userService;
    private final ConcernService concernService;

    @Autowired
    public ConcernController(UserService userService, ConcernService concernService){
        this.userService = userService;
        this.concernService = concernService;
    }

    /**
     * @Description: 用户关注与取关
     * @Date: 2023/1/20
     **/
    @GetMapping("/user")
    public APIResponse getUserConcern(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      HttpSession session){
        return concernService.getAllUSerConcern(pageNum, pageSize, session);
    }

    @PostMapping("/user")
    public APIResponse addUserConcern(@RequestParam("uid") String uid,
                                      HttpSession session){
        return concernService.addUserConcernByUid(uid, session);
    }

    @DeleteMapping("/user")
    public APIResponse deleteUserConcern(@RequestParam("uid") String uid,
                                         HttpSession session){
        return concernService.deleteUserConcernByUid(uid, session);
    }

    /**
     * @Description: 帖子关注与取关
     * @Date: 2023/1/20
     **/
    @GetMapping("/post")
    public APIResponse getPostConcern(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      HttpSession session){
        return concernService.getAllPostConcern(pageNum, pageSize, session);
    }

    @PostMapping("/post")
    public APIResponse addPostConcern(@RequestParam("pid") Integer pid,
                                      HttpSession session){
        return concernService.addPostConcernByUid(pid, session);
    }

    @DeleteMapping("/post")
    public APIResponse deletePostConcern(@RequestParam("pid") Integer pid,
                                         HttpSession session){
        return concernService.deletePostConcernByUid(pid, session);
    }
}
