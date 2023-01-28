package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.UserMapper;
import com.example.corner.pojo.User;
import com.example.corner.service.LogService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.example.corner.uitls.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;


/**
 * @Time : 2023/1/19-21:36
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *登陆实现层*
 */

@Service
public class LogServiceImpl extends ServiceImpl<UserMapper, User> implements LogService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: 账验证账密并且返回用户对象
     * @Date: 2023/1/20
     * @Param uid: 账号
     * @Param pwd: 密码
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse loginWithAccountAndPwd(String uid, String pwd) {
        try {
            if (userMapper.verifyTheAccountAndPassword(uid, pwd)) {
                return APIResponse.success(userMapper.getUserFullByUid(uid));
            }
            return APIResponse.error(ErrorCode.NO_SUCH_USER);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"loginWithAccountAndPwd");
        }
    }

    /**
     * @Description: 添加用户主信息
     * @Date: 2023/1/20
     * @Param uid: 用户账户
     * @Param pwd: 用户密码
     * @Param nickname: 用户昵称
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse addUserMainAccount(String uid, String pwd, String nickname) {
        try {
            if(uid == null || pwd == null || nickname == null){
                return APIResponse.error(ErrorCode.MISSING_INFORMATION_ERROR, "请检查信息是否填写完整");            }
            if(userMapper.getUserMainByUid(uid) != null){
                return APIResponse.error(ErrorCode.USER_ALREADY_EXISTS);
            }
            Date date = new Date(System.currentTimeMillis());
            String resultPwd = MD5Utils.inputPassToFormPass(pwd);
            if (userMapper.addUserMain(uid, resultPwd, nickname, date, date)){
                return APIResponse.success("注册成功");
            }
            return APIResponse.error(ErrorCode.SQL_ERROR, "addUserMain;[LogService]");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"addUserAccount;[LogService]");
        }
    }

}
