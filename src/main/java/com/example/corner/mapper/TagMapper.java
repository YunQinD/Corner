package com.example.corner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.corner.pojo.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @Time : 2023/1/24-23:56
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    @Insert("INSERT INTO post_tag(content, category, creator_id, created_at) \n" +
            "VALUES (#(content), #(category), #(creatorId), #(createdAt));")
    boolean addTag(@Param("content") String content,
                   @Param("category") String category,
                   @Param("creatorId") String creatorId,
                   @Param("createdAt") Date createdAt);

    @Select("SELECT * FROM post_tag;")
    List<Tag> getAllTags();

    @Select("SELECT * FROM post_tag WHERE category = #{category};")
    List<Tag> getTagsByCategory(@Param("category") String category);

    @Select("SELECT *\n" +
            "FROM post_tag\n" +
            "WHERE content REGEXP '#{content}';")
    List<Tag> getContentLikeTag(@Param("content") String content);

    @Select("SELECT *\n" +
            "FROM post_tag\n" +
            "WHERE creator_id = #{creatorId};")
    List<Tag> getTagByCreatorId (@Param("creatorId") String creatorId);
}
