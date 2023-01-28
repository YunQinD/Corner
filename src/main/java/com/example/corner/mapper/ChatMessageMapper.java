package com.example.corner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.corner.pojo.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * @Time : 2023/1/21-11:01
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    /**添加私聊消息*/
    @Insert("INSERT INTO chat_private (host_id, peer_id, message_type, content, host_status, peer_status, send_at, updated_at)\n" +
            "VALUES (#{hostId},#{peerId},#{messageType},#{content},0,0,#{sendAt},#{updatedAt});")
    boolean addPrivateChatMessage(@Param("messageType") Integer messageType,
                                  @Param("hostId") String hostId,
                                  @Param("peerId") String peerId,
                                  @Param("content") String content,
                                  @Param("sendAt") Date sendAt,
                                  @Param("updatedAt") Date updatedAt);

    /**通过信息和时间设置发件人状态为删除*/
    @Update("UPDATE chat_private \n" +
            "SET host_status = 3, updated_at = #{updatedAt}\n" +
            "WHERE content = #{message} AND send_at = #{sendAt} AND host_id = #{hostId} AND peer_id = #{peerId};")
    boolean deleteHostMessageByMessageAndDate(@Param("updatedAt") Date updatedAt,
                                              @Param("message") String message,
                                              @Param("sendAt") Date sendAt,
                                              @Param("hostId") String hostId,
                                              @Param("peerId") String peerId);

    /**通过信息和时间设置收件人状态为删除*/
    @Update("UPDATE chat_private \n" +
            "SET peer_status = 3, updated_at = #{updatedAt}\n" +
            "WHERE content = #{message} AND send_at = #{sendAt} AND host_id = #{hostId} AND peer_id = #{peerId};")
    boolean deletePeerMessageByMessageAndDate(@Param("updatedAt") Date updatedAt,
                                              @Param("message") String message,
                                              @Param("sendAt") Date sendAt,
                                              @Param("hostId") String hostId,
                                              @Param("peerId") String peerId);

    /**通过信息和时间撤回消息*/
    @Update("UPDATE chat_private \n" +
            "SET host_status = 2, updated_at = #{updatedAt}\n" +
            "WHERE content = #{message} AND send_at = #{sendAt} AND host_id = #{hostId} AND peer_id = #{peerId};")
    boolean withdrawMessageByMessageAndDate(@Param("updatedAt") Date updatedAt,
                                            @Param("message") String message,
                                            @Param("sendAt") Date sendAt,
                                            @Param("hostId") String hostId,
                                            @Param("peerId") String peerId);

    //TODO 下次改版后再添加已读
    /**收件人设置已读*/
    @Update("UPDATE chat_private \n" +
            "SET peer_status = 1, updated_at = #{updatedAt}\n" +
            "WHERE content = #{message} AND send_at = #{sendAt};")
    boolean setPeerStatusAsHaveReadByMessageAndDate(@Param("updatedAt") Date updatedAt,
                                                    @Param("message") String message,
                                                    @Param("sendAt") Date sendAt);

    /**通过信息和时间获取私聊消息*/
    @Select("SELECT *\n" +
            "FROM chat_private\n" +
            "WHERE content = #{message} AND send_at = #{date} AND host_id = #{hostId} AND peer_id = #{peerId};")
    ChatMessage getChatMessageByMessageAndDate(@Param("message") String message,
                                               @Param("date") Date date,
                                               @Param("hostId") String hostId,
                                               @Param("peerId") String peerId);

    /**获取所有消息*/
    @Select("SELECT *\n" +
            "FROM chat_private;")
    List<ChatMessage> getAllChatMessage();

    /**获取发信方可见消息*/
    @Select("SELECT *\n" +
            "FROM chat_private\n" +
            "WHERE host_id = #{hostId} AND peer_id = #{peerId} AND host_status <> 2 AND host_status <> 3 AND peer_status <> 2;")
    List<ChatMessage> getVisibleMessage(@Param("hostId") String hostId,
                                        @Param("peerId") String peerId);

}
