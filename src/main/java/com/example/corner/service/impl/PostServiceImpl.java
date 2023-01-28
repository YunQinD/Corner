package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.PostMapper;
import com.example.corner.pojo.Post;
import com.example.corner.pojo.User;
import com.example.corner.service.PostService;
import com.example.corner.service.TeamService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.example.corner.uitls.FileUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Time : 2023/1/25-16:46
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final PostMapper postMapper;
    private final TeamService teamService;
    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    public PostServiceImpl(PostMapper postMapper,
                           TeamService teamService){
        this.postMapper = postMapper;
        this.teamService = teamService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse addPost(String title, Integer teamNum, Date goalTime, String content, MultipartFile picture, HttpSession session) {
        lock.lock();
        try {
            Date date = new Date(System.currentTimeMillis());
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            APIResponse pictureResponse = FileUtils.savePictures(picture);
            APIResponse thumbResponse = FileUtils.thumbnail(picture);
            String pictureUrl = pictureResponse.getMessage();
            String thumbUrl = thumbResponse.getMessage();
            if (postMapper.addPost(uid, title, teamNum, goalTime, thumbUrl, date, date, pictureUrl, content)) {
                Post post = postMapper.getPostByCreatorIdAndCreatedAt(uid, date);
                Integer postId = post.getId();
                teamService.creatTeam(postId, uid, teamNum, uid);
                return APIResponse.success("帖子发送成功");
            }
            return APIResponse.error(ErrorCode.POST_SERVER_ERROR,"帖子发送失败");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getAllPost(Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Post> postList = postMapper.getAllPost();
            PageInfo<Post> postPageInfo = new PageInfo<Post>(postList);
            return APIResponse.success(postPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getMyPost(Integer pageNum, Integer pageSize, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            String creatorId = user.getUid();
            PageHelper.startPage(pageNum, pageSize);
            List<Post> postList = postMapper.getPostByCreatorId(creatorId);
            PageInfo<Post> postPageInfo = new PageInfo<Post>(postList);
            return APIResponse.success(postPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getPostByCreatorId(Integer pageNum, Integer pageSize, String creatorId) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Post> postList = postMapper.getPostByCreatorId(creatorId);
            PageInfo<Post> postPageInfo = new PageInfo<Post>(postList);
            return APIResponse.success(postPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getPostByCategory(Integer pageNum, Integer pageSize, String category) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Post> postList = postMapper.getPostByCategory(category);
            PageInfo<Post> postPageInfo = new PageInfo<Post>(postList);
            return APIResponse.success(postPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getPostByGoalTime(Integer pageNum, Integer pageSize, Date beginTime, Date endTime) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Post> postList = postMapper.getPostByGoalTime(beginTime, endTime);
            PageInfo<Post> postPageInfo = new PageInfo<Post>(postList);
            return APIResponse.success(postPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getPostByTitleAndContent(Integer pageNum, Integer pageSize, String content) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Post> postList = postMapper.getPostByTitleAndContent(content);
            PageInfo<Post> postPageInfo = new PageInfo<Post>(postList);
            return APIResponse.success(postPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getPostByTeamNum(Integer pageNum, Integer pageSize, Integer teamNum) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Post> postList = postMapper.getPostByTeamNum(teamNum);
            PageInfo<Post> postPageInfo = new PageInfo<Post>(postList);
            return APIResponse.success(postPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse deletePost(Integer postId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            Date date = new Date(System.currentTimeMillis());
            Post post = postMapper.getPostByPostId(postId);
            if (post.getIsDeleted() == 1 || uid.equals(post.getCreatorId())) {
                return APIResponse.error(ErrorCode.POST_SERVER_ERROR, "帖子已删除或用户无权限");
            }
            if (postMapper.setIsDeletedAsTrueByPostId(date, postId)) {
                return APIResponse.success("帖子删除成功");
            }
            return APIResponse.error(ErrorCode.POST_SERVER_ERROR, "帖子已删除或用户无权限");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
    }


}
