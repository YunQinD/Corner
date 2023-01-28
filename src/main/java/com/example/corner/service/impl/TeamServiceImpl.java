package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.TeamMapper;
import com.example.corner.pojo.Team;
import com.example.corner.service.TeamService;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Time : 2023/1/25-18:09
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    private final TeamMapper teamMapper;
    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    public TeamServiceImpl(TeamMapper teamMapper){
        this.teamMapper = teamMapper;
    }

    /*
     * @Description: 队伍管理相关
     * @Date: 2023/1/26
     */

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse creatTeam(Integer postId, String leaderId, Integer teamNum, String memberId) {
        lock.lock();
        try {
            Date date = new Date(System.currentTimeMillis());
            if (teamMapper.addTeamMember(postId, leaderId, teamNum, memberId, date, date)) {
                return APIResponse.success("创建队伍成功");
            }
            return APIResponse.error(ErrorCode.TEAM_SERVER_ERROR, "创建队伍失败");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse joinTeam(Integer postId, String memberId) {
        lock.lock();
        try {
            Date date = new Date(System.currentTimeMillis());
            Team targetTeam = teamMapper.selectOneTeamRecord(postId);
            if (teamMapper.addTeamMember(postId,
                    targetTeam.getLeaderId(),
                    targetTeam.getTeamNum(),
                    memberId,
                    targetTeam.getCreatedAt(),
                    date)) {
                return APIResponse.success("加入队伍成功");
            }
            return APIResponse.error(ErrorCode.TEAM_SERVER_ERROR, "加入队伍失败");
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public APIResponse getAllTeams(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public APIResponse addApplyToOtherTeam() {
        return null;
    }

    @Override
    public APIResponse getUsersSendApplyToMyTeam() {
        return null;
    }

    @Override
    public APIResponse getAllMyApply() {
        return null;
    }

    @Override
    public APIResponse leaderAgreeApplicantsApply() {
        return null;
    }

    @Override
    public APIResponse leaderRejectApplicantsApply() {
        return null;
    }

    @Override
    public boolean checkIfTeamIsFull() {
        return false;
    }

    @Override
    public Integer getMemberNumOfTeam() {
        return null;
    }


}
