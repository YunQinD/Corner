package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.FeedBackMapper;
import com.example.corner.pojo.FeedBack;
import com.example.corner.pojo.User;
import com.example.corner.service.FeedBackService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Date;

/**
 * @Time : 2023/1/18-20:58
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */

@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {
    @Autowired
    private FeedBackMapper feedBackMapper;

    /**
     * @Description: 保存反馈
     * @Date: 2023/1/18
     * @Param content: 反馈内容
     * @Param session: session
     * @return: com.example.corner.uitls.APIResponse
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse saveFeedBack(String content, HttpSession session) {
        try {
            FeedBack feedBack = new FeedBack();
            User user = (User) session.getAttribute("user");
            feedBack.setUid(user.getUid());
            feedBack.setContent(content);
            feedBack.setCreatedAt(new Date(System.currentTimeMillis()));
            if (feedBackMapper.insert(feedBack) == 0) {
                return APIResponse.error(ErrorCode.SQL_ERROR, "Feedback Insert Error");
            }
            return APIResponse.success("Feedback is successful");
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }
}
