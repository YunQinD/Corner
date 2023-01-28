package com.example.corner.controller;

import com.example.corner.service.UserService;
import com.example.corner.uitls.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Time : 2023/1/20-21:37
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *用户接口层*
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**注销用户*/
    @DeleteMapping
    public APIResponse logOutUser(@RequestParam("pwd") String pwd,
                                  @RequestParam("code") String code,
                                  HttpSession session){
        return userService.logOutUserWithPwdAndCode(pwd, code, session);
    }


}
