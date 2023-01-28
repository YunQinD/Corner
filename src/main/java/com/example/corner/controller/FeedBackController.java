package com.example.corner.controller;

import com.example.corner.service.FeedBackService;
import com.example.corner.uitls.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Time : 2023/1/18-21:00
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *提交反馈*
 */

@RestController
@RequestMapping("/feedbacks")
public class FeedBackController {

    private final FeedBackService feedBackService;

    @Autowired
    private FeedBackController(FeedBackService feedBackService){
        this.feedBackService = feedBackService;
    }

    @PostMapping
    public APIResponse submit(@RequestParam("content") String content,
                              HttpSession session){
        return feedBackService.saveFeedBack(content, session);
    }
}
