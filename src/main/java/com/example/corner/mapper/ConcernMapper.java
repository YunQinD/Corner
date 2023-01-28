package com.example.corner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.corner.pojo.Concern;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * @Time : 2023/1/20-23:22
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Mapper
public interface ConcernMapper extends BaseMapper<Concern> {

    /*
     * @Description: 用户关注
     * @Date: 2023/1/21
     **/

    /**添加用户关注*/
    @Insert("INSERT INTO users_friend_concern (uid, friend_id, created_at, updated_at) VALUES (#{uid},#{friendId},#{createdAt},#{updatedAt});")
    boolean addUserConcernByUidAndFriendId(String uid, String friendId, Date createdAt, Date updatedAt);

    /**取消用户关注*/
    @Update("UPDATE users_friend_concern SET is_deleted = 1, updated_at = #{updatedAt} WHERE uid = #{uid} AND friend_id = #{friendId} AND is_deleted = 0;")
    boolean deleteUserConcernByUidAndFriendId(@Param("updatedAt") Date updatedAt,
                                              @Param("uid") String uid,
                                              @Param("friendId") String friendId);

    /**查询所有关注列表*/
    @Select("SELECT * FROM users_friend_concern WHERE uid = #{uid} AND is_deleted = 0;")
    List<Concern> getAllUsersConcernList(String uid);

    /**查询单独的关注*/
    @Select("SELECT * FROM users_friend_concern WHERE uid = #{uid} AND friend_id = #{friendId} AND is_deleted = 0;")
    Concern getCertainUsersConcern(String uid, String friendId);

    /*
     * @Description: 帖子关注
     * @Date: 2023/1/21
     **/

    /**添加帖子关注*/
    @Insert("INSERT INTO users_post_concern (uid, pid, created_at, updated_at) VALUES (#{uid},#{pid},#{createdAt},#{updatedAt});")
    boolean addPostConcernByUidAndPid(String uid, Integer pid, Date createdAt, Date updatedAt);

    /**取消帖子关注*/
    @Update("UPDATE users_post_concern SET is_deleted = 1, updated_at = #{updatedAt} WHERE uid = #{uid} AND pid = #{pid} AND is_deleted = 0;")
    boolean deletePostConcernByUidAndFriendId(@Param("updatedAt") Date updatedAt,
                                              @Param("uid") String uid,
                                              @Param("pid") Integer pid);

    /**查询所有帖子关注列表*/
    @Select("SELECT * FROM users_post_concern WHERE uid = #{uid} AND is_deleted = 0;")
    List<Concern> getAllPostsConcernList(String uid);

    /**查询单独的关注*/
    @Select("SELECT * FROM users_post_concern WHERE uid = #{uid} AND pid = #{pid} AND is_deleted = 0;")
    Concern getCertainPostsConcern(String uid, Integer pid);
}
