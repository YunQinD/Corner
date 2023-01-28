package com.example.corner.controller;

import com.example.corner.pojo.User;
import com.example.corner.service.LogService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.example.corner.uitls.JwtUtil;
import com.example.corner.uitls.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.sql.Date;

/**
 * @Time : 2023/1/6-22:55
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *登陆管理接口*
 */
@RestController
@RequestMapping("/login")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService){
        this.logService = logService;
    }

    //TODO 密码加密存储，账号注册

    /**
     * @Description: 账密登陆，返回token
     * @Date: 2023/1/20
     */
    @PostMapping
    public APIResponse login(@RequestParam("uid") String uid,
                             @RequestParam("pwd") String pwd,
                             HttpSession httpSession){
        try {
            String resultPwd = MD5Utils.inputPassToFormPass(pwd);
            User user = (User) logService.loginWithAccountAndPwd(uid, resultPwd).getResult();
            if (user != null){
                String token = JwtUtil.generateToken(uid);
                httpSession.setAttribute("user", user);
                return APIResponse.success(token);
            }
            return APIResponse.error(ErrorCode.NO_SUCH_USER);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "login");
        }
    }

    @PostMapping("/register")
    public APIResponse register(@RequestParam("uid")      String uid,
                                @RequestParam("pwd")      String pwd,
                                @RequestParam("nickname") String nickname
                                ){
        return logService.addUserMainAccount(uid, pwd, nickname);
    }
}
