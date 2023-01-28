package com.example.corner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.corner.pojo.Post;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * @Time : 2023/1/25-16:44
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    @Insert("INSERT INTO post_main(creator_id, title, team_num, goal_time, thumbnail_url, created_at, updated_at, picture_url, content)\n" +
            "VALUES (#{creatorId}, #{title}, #{teamNum}, #{goalTime}, #{thumbnailUrl}, #{createdAt}, #{updatedAt}, #{pictureUrl}, #{content});")
    boolean addPost(@Param("creatorId") String creatorId,
                    @Param("title") String title,
                    @Param("teamNum") Integer teamNum,
                    @Param("goalTime") Date goalTime,
                    @Param("thumbnailUrl") String thumbnailUrl,
                    @Param("createdAt") Date createdAt,
                    @Param("updatedAt") Date updatedAt,
                    @Param("pictureUrl") String pictureUrl,
                    @Param("content") String content);

    @Select("SELECT * FROM post_main WHERE creator_id = #{creatorId} AND created_at = #{createdAt} AND is_deleted = 0;")
    Post getPostByCreatorIdAndCreatedAt(@Param("creatorId") String creatorId,
                                        @Param("createdAt") Date createdAt);

    @Select("SELECT * FROM post_main WHERE id = #{id};")
    Post getPostByPostId(@Param("id") Integer postId);

    @Select("SELECT * FROM post_main WHERE is_deleted = 0")
    List<Post> getAllPost();

    @Select("SELECT * FROM post_main WHERE creator_id = #{creatorId} AND is_deleted = 0;")
    List<Post> getPostByCreatorId(@Param("creatorId") String creatorId);

    @Select("SELECT * FROM post_main WHERE category = #{category} AND is_deleted = 0;")
    List<Post> getPostByCategory(@Param("category") String category);

    @Select("SELECT *\n" +
            "FROM post_main\n" +
            "WHERE is_deleted = 0\n" +
            "  AND created_at\n" +
            "      BETWEEN #{beginTime} \n" +
            "      AND #{endTime};")
    List<Post> getPostByGoalTime(@Param("beginTime") Date beginTime,
                                 @Param("endTime") Date endTime);

    @Select("SELECT * FROM post_main WHERE team_num = #{teamNum} AND is_deleted = 0;")
    List<Post> getPostByTeamNum(@Param("teamNum") Integer teamNum);

    @Select("SELECT * \n" +
            "FROM post_main \n" +
            "WHERE is_deleted = 0 \n" +
            "  AND content REGEXP '#{content}' \n" +
            "   OR title REGEXP '#{content}';")
    List<Post> getPostByTitleAndContent(@Param("content") String content);

    @Update("UPDATE post_main\n" +
            "SET is_deleted = 1, updated_at = #{updatedAt}\n" +
            "WHERE id = #{id};")
    boolean setIsDeletedAsTrueByPostId(@Param("updatedAt") Date updatedAt,
                                       @Param("id") Integer postId);

}
