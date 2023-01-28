package com.example.corner.controller;

import com.example.corner.service.PostService;
import com.example.corner.service.TagService;
import com.example.corner.uitls.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.Date;

/**
 * @Time : 2023/1/24-23:43
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final TagService tagService;
    private final PostService postService;

    @Autowired
    public PostController(TagService tagService,
                          PostService postService){
        this.tagService = tagService;
        this.postService = postService;
    }

    /*
     * @Description: Tag相关管理
     * @Date: 2023/1/24
     */

    /**添加tag*/
    @PostMapping("/tags/add")
    public APIResponse addTag(@RequestParam("content") String content,
                              @RequestParam("category") String category,
                              HttpSession session){
        return tagService.addTag(content, category, session);
    }

    /**获取所有Tag*/
    @GetMapping("/tags")
    public APIResponse getAllTags(Integer pageNum, Integer pageSize){
        return tagService.getAllTags(pageNum, pageSize);
    }

    /**获取用户定义的所有Tag*/
    @GetMapping("/tags/myTags")
    public APIResponse getMyTags(HttpSession session){
        return tagService.getMyTags(session);
    }

    /**按内容模糊查询Tag*/
    @GetMapping("/tags/content")
    public APIResponse getContentLkeTags(Integer pageNum, Integer pageSize, String content){
        return tagService.getContentLikeTags(pageNum, pageSize, content);
    }

    /**按分类获取Tag*/
    @GetMapping("/tags/category")
    public APIResponse getTagsByCategory(Integer pageNum, Integer pageSize, String category){
        return tagService.getTagsByCategory(pageNum, pageSize, category);
    }


    /*
     * @Description: Post相关管理
     * @Date: 2023/1/24
     */

    @PostMapping("/posts")
    public APIResponse addPost(@RequestParam("title") String title,
                               @RequestParam("teamNum") Integer teamNum,
                               @RequestParam("goalTime") Date goalTime,
                               @RequestParam("content") String content,
                               @RequestParam("file") MultipartFile picture,
                               HttpSession session){
        return postService.addPost(title, teamNum, goalTime, content, picture, session);
    }

    @GetMapping("/posts/all")
    public APIResponse getAllPost(Integer pageNum, Integer pageSize){
        return postService.getAllPost(pageNum, pageSize);
    }

    @GetMapping("/posts/myPost")
    public APIResponse getMyPost(Integer pageNum, Integer pageSize, HttpSession session){
        return postService.getMyPost(pageNum, pageSize, session);
    }

    @GetMapping("/posts/creatorId")
    public APIResponse getPostByCreatorId(Integer pageNum, Integer pageSize, String creatorId){
        return postService.getPostByCreatorId(pageNum, pageSize, creatorId);
    }

    @GetMapping("/posts/category")
    public APIResponse getPostByCategory(Integer pageNum, Integer pageSize, String category){
        return postService.getPostByCategory(pageNum, pageSize, category);
    }

    @GetMapping("/posts/goalTime")
    public APIResponse getPostByGoalTime(Integer pageNum,
                                         Integer pageSize,
                                         @RequestParam("beginTime") Date beginTime,
                                         @RequestParam("endTime") Date endTime){
        return postService.getPostByGoalTime(pageNum, pageSize, beginTime, endTime);
    }

    @GetMapping("/posts/content")
    public APIResponse getPostByTitleAndContent(Integer pageNum, Integer pageSize, String content){
        return postService.getPostByTitleAndContent(pageNum, pageSize, content);
    }

    @GetMapping("/posts/teamNum")
    public APIResponse getPostByTeamNum(Integer pageNum, Integer pageSize, Integer teamNum){
        return postService.getPostByTeamNum(pageNum, pageSize, teamNum);
    }

    @PostMapping("/posts/delete")
    public APIResponse deletePost(Integer postId, HttpSession session){
        return postService.deletePost(postId, session);
    }

}
