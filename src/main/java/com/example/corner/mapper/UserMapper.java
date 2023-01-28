package com.example.corner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.corner.pojo.Concern;
import com.example.corner.pojo.User;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * @Time : 2023/1/18-20:39
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**按uid获取用户全部信息*/
    @Select("SELECT users_main.id,\n" +
            "       users_main.uid,\n" +
            "       avatar_url,\n" +
            "       pwd,\n" +
            "       nickname,\n" +
            "       status,\n" +
            "       real_name,\n" +
            "       gender,\n" +
            "       campus,\n" +
            "       major,\n" +
            "       grade,\n" +
            "       level,\n" +
            "       role,\n" +
            "       users_main.created_at,\n" +
            "       users_main.updated_at\n" +
            "FROM users_main,\n" +
            "     users_extends,\n" +
            "     users_status \n" +
            "WHERE users_main.uid = users_extends.uid = #{uid};")
    User getUserFullByUid(String uid);

    /**按uid获取用户主要信息*/
    @Select("SELECT * FROM users_main WHERE uid = #{uid};")
    User getUserMainByUid(String uid);

    /**账密验证*/
    @Select("SELECT * FROM users_main WHERE uid = #{uid} AND pwd = #{pid};")
    boolean verifyTheAccountAndPassword(String uid, String pid);

    /**按uid更新头像*/
    @Update("UPDATE users_main SET avatar_url = #{url} WHERE uid = #{uid};")
    boolean updateAvatarByUid(String url,String uid);

    /**添加用户主要信息*/
    @Insert("INSERT INTO users_main (uid,pwd,nickname,created_at,updated_at) VALUES (#{uid},#{pwd},#{nickname},#{createdAt},#{updatedAt});")
    boolean addUserMain(String uid, String pwd, String nickname, Date createdAt, Date updatedAt);

    /**用uid更新用户状态*/
    @Update("UPDATE users_main\n" +
            "SET status = #{status}, updated_at = #{updatedAt}\n" +
            "WHERE uid = #{uid};")
    boolean updateUserStatusByUid(@Param("status") Integer status,
                                  @Param("updatedAt") Date updatedAt,
                                  @Param("uid") String uid);



}
