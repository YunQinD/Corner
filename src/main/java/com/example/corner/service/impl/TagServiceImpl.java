package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.TagMapper;
import com.example.corner.pojo.ChatMessage;
import com.example.corner.pojo.Tag;
import com.example.corner.pojo.User;
import com.example.corner.service.TagService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

/**
 * @Time : 2023/1/24-23:55
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final TagMapper tagMapper;

    @Autowired
    private TagServiceImpl(TagMapper tagMapper){
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse addTag(String content, String category, HttpSession session) {
        try {
            User user = (User)session.getAttribute("user");
            String uid = user.getUid();
            Date date = new Date(System.currentTimeMillis());
            if(tagMapper.addTag(content, category, uid, date)){
                return APIResponse.success("Tag添加成功");
            }
            return APIResponse.error(ErrorCode.TAG_ERROR,"Tag添加失败");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"addTag;[TagServiceImpl]");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getAllTags(Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Tag> tagList = tagMapper.getAllTags();
            PageInfo<Tag> tagPageInfo = new PageInfo<Tag>(tagList);
            return APIResponse.success(tagPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"getAllTags;[TagServiceImpl]");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getTagsByCategory(Integer pageNum, Integer pageSize, String category) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Tag> tagList = tagMapper.getTagsByCategory(category);
            PageInfo<Tag> tagPageInfo = new PageInfo<Tag>(tagList);
            return APIResponse.success(tagPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"getTagsByCategory;[TagServiceImpl]");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getMyTags(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            String nowUser = user.getUid();
            List<Tag> tagList = tagMapper.getTagByCreatorId(nowUser);
            return APIResponse.success(tagList);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"getMyTags;[TagServiceImpl]");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getContentLikeTags(Integer pageNum, Integer pageSize, String content) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Tag> tagList = tagMapper.getContentLikeTag(content);
            PageInfo<Tag> tagPageInfo = new PageInfo<Tag>(tagList);
            return APIResponse.success(tagPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"getContentLikeTags;[TagServiceImpl]");
        }
    }
}
