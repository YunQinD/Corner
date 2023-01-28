package com.example.corner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.corner.pojo.Team;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

/**
 * @Time : 2023/1/25-18:09
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

    @Insert("INSERT INTO post_team(post_id, leader_id, team_num, member_id, created_at, join_at) \n" +
            "VALUES (#{postId}, #{leaderId}, #{teamNum}, #{memberId}, #{createdAt}, #{joinAt});")
    boolean addTeamMember(Integer postId, String leaderId, Integer teamNum, String memberId, Date createdAt, Date joinAt);

    @Select("SELECT * FROM post_team WHERE post_id = #{postId} ORDER BY created_at LIMIT 1;")
    Team selectOneTeamRecord(@Param("postId") Integer postId);

    @Select("")
    List<Team> selectAllTeam();
}
