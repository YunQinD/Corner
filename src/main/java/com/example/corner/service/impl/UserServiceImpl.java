package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.controller.UserController;
import com.example.corner.mapper.UserMapper;
import com.example.corner.pojo.Concern;
import com.example.corner.pojo.User;
import com.example.corner.service.LogService;
import com.example.corner.service.UserService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.example.corner.uitls.JwtUtil;
import com.example.corner.uitls.MD5Utils;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Time : 2023/1/19-22:59
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final LogService logService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           LogService logService){
        this.userMapper = userMapper;
        this.logService = logService;
    }

    private ReentrantLock lock = new ReentrantLock();

    /**通过Uid获取用户对象*/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public User getUserByUid(String uid) {
        try {
            return userMapper.getUserFullByUid(uid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: 账密+验证码注销账户
     * @Date: 2023/1/24
     * @Param pwd: 密码
     * @Param code: 用户输入的验证码
     * @Param session: 获取用户和code
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse logOutUserWithPwdAndCode(String pwd, String code, HttpSession session) {
        lock.lock();
        try {
            String resultPwd = MD5Utils.inputPassToFormPass(pwd);
            User nowUser = (User) session.getAttribute("user");
            String uid = nowUser.getUid();
            User user = (User) logService.loginWithAccountAndPwd(uid, resultPwd).getResult();
            Date date = new Date(System.currentTimeMillis());
            String sessionCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            System.out.println(" session中获取的验证码:  " + sessionCode);
            if (user != null) {
                if (code.equalsIgnoreCase(sessionCode)) {
                    userMapper.updateUserStatusByUid(1, date, uid);
                    return APIResponse.success("验证码相同,注销成功");
                } else {
                    return APIResponse.error(ErrorCode.CAPTCHA_ERROR, "验证码不同,注销失败");
                }
            }
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "用户密码错误或用户不存在");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"logOutUserWithPwdAndCode;[UserServiceImpl]");
        }finally {
            lock.unlock();
        }
    }


}
