package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.ConcernMapper;
import com.example.corner.pojo.Concern;
import com.example.corner.pojo.User;
import com.example.corner.service.ConcernService;
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
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Time : 2023/1/20-23:21
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Service
public class ConcernServiceImpl extends ServiceImpl<ConcernMapper, Concern> implements ConcernService {

    private ReentrantLock lock = new ReentrantLock();
    private ConcernMapper concernMapper;

    @Autowired
    public ConcernServiceImpl(ConcernMapper concernMapper){
        this.concernMapper = concernMapper;
    }

    /*
      @Description: 用户关注与取关
     * @Date: 2023/1/20
     */

    /**通过uid关注用户*/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse addUserConcernByUid(String friendId, HttpSession session) {
        lock.lock();
        try{
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            Date date = new Date(System.currentTimeMillis());
            Concern concern = concernMapper.getCertainUsersConcern(uid, friendId);
            if (concern != null){
                return APIResponse.error(ErrorCode.CONCERN_ERROR,"您已经关注过了喵~");
            }
            if(concernMapper.addUserConcernByUidAndFriendId(uid, friendId, date, date)){
                return APIResponse.success("成功关注喵~");
            }
            return APIResponse.error(ErrorCode.CONCERN_ERROR,"关注失败喵~");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "addUserConcernByUid;[ConcernServiceImpl]");
        }finally {
            lock.unlock();
        }
    }

    /**通过uid取关用户*/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse deleteUserConcernByUid(String friendId, HttpSession session) {
        lock.lock();
        try{
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            Date date = new Date(System.currentTimeMillis());
            Concern concern = concernMapper.getCertainUsersConcern(uid, friendId);
            if (concern == null){
                return APIResponse.error(ErrorCode.CONCERN_ERROR,"您还没关注喵~");
            }
            if (concernMapper.deleteUserConcernByUidAndFriendId(date, uid, friendId)){
                return APIResponse.success("取关成功喵~");
            }
            return APIResponse.error(ErrorCode.CONCERN_ERROR,"取关失败喵~");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "deleteUserConcernByUid;[ConcernServiceImpl]");
        }finally {
            lock.unlock();
        }
    }

    /**获取所有用户帖子*/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getAllUSerConcern(Integer pageNum, Integer pageSize, HttpSession session) {
        lock.lock();
        try {
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            PageHelper.startPage(pageNum, pageSize);
            List<Concern> concernList = concernMapper.getAllUsersConcernList(uid);
            PageInfo<Concern> concernPageInfo = new PageInfo<Concern>(concernList);
            return APIResponse.success(concernPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"getAllUSerConcern;[ConcernServiceImpl]");
        }finally {
            lock.unlock();
        }

    }

    /*
      @Description: 帖子的关注与取关
     * @Date: 2023/1/20
     */

    /**通过uid关注帖子*/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse addPostConcernByUid(Integer pid, HttpSession session) {
        lock.lock();
        try{
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            Date date = new Date(System.currentTimeMillis());
            Concern concern = concernMapper.getCertainPostsConcern(uid, pid);
            if (concern != null){
                return APIResponse.error(ErrorCode.CONCERN_ERROR,"您已经关注过了喵~");
            }
            if(concernMapper.addPostConcernByUidAndPid(uid, pid, date, date)){
                return APIResponse.success("成功关注喵~");
            }
            return APIResponse.error(ErrorCode.CONCERN_ERROR,"关注失败喵~");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "addPostConcernByUid;[ConcernServiceImpl]");
        }finally {
            lock.unlock();
        }
    }

    /**通过uid取关帖子*/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse deletePostConcernByUid(Integer pid, HttpSession session) {
        lock.lock();
        try{
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            Date date = new Date(System.currentTimeMillis());
            Concern concern = concernMapper.getCertainPostsConcern(uid, pid);
            if (concern == null){
                return APIResponse.error(ErrorCode.CONCERN_ERROR,"您还没关注喵~");
            }
            if (concernMapper.deletePostConcernByUidAndFriendId(date, uid, pid)){
                return APIResponse.success("取关成功喵~");
            }
            return APIResponse.error(ErrorCode.CONCERN_ERROR,"取关失败喵~");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "deletePostConcernByUid;[ConcernServiceImpl]");
        }finally {
            lock.unlock();
        }
    }

    /**获取所有关注帖子*/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse getAllPostConcern(Integer pageNum, Integer pageSize, HttpSession session) {
        lock.lock();
        try {
            User user = (User) session.getAttribute("user");
            String uid = user.getUid();
            PageHelper.startPage(pageNum, pageSize);
            List<Concern> concernList = concernMapper.getAllPostsConcernList(uid);
            PageInfo<Concern> concernPageInfo = new PageInfo<Concern>(concernList);
            return APIResponse.success(concernPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR,"getAllUSerConcern;[ConcernServiceImpl]");
        }finally {
            lock.unlock();
        }
    }
}
