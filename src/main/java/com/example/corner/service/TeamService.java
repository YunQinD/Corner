package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.Team;
import com.example.corner.uitls.APIResponse;

import java.sql.Date;

/**
 * @Time : 2023/1/25-18:08
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface TeamService extends IService<Team> {
    APIResponse creatTeam(Integer postId, String leaderId, Integer teamNum, String memberId);
    APIResponse joinTeam(Integer postId, String memberId);
    APIResponse getAllTeams(Integer pageNum, Integer pageSize);

    APIResponse addApplyToOtherTeam();
    APIResponse getUsersSendApplyToMyTeam();
    APIResponse getAllMyApply();
    APIResponse leaderAgreeApplicantsApply();
    APIResponse leaderRejectApplicantsApply();

    boolean checkIfTeamIsFull();
    Integer getMemberNumOfTeam();

}
