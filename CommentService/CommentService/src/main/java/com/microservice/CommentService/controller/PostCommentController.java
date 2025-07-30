package com.microservice.CommentService.controller;

import com.microservice.CommentService.dto.PostCommentBind;
import com.microservice.CommentService.dto.PostCommentDto;
import com.microservice.CommentService.entity.PostComment;
import com.microservice.CommentService.exceptions.CustomException;
import com.microservice.CommentService.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin({"http://localhost:5173/", "http://localhost:8005/"})
@RequestMapping("/main/comments")
public class PostCommentController {
    @Autowired
    private PostCommentService postCommentService;
    @PostMapping("/createcomment")
    public PostCommentDto createComment(@RequestBody PostCommentBind postCommentBind,
                                        @RequestHeader("Authorization") String token) throws CustomException {

        return postCommentService.createCommentService(postCommentBind,token);
    }
    @GetMapping("/allcomments")
    public List<PostCommentDto> getAllPostComments(@RequestParam("postId") Long postId,
                                   @RequestParam(defaultValue = "0")Integer page,
                                   @RequestParam(defaultValue = "1")Integer size,
                                   @RequestHeader("Authorization") String token) throws CustomException {
        PageRequest pageRequest=PageRequest.of(page,size);
        List<PostCommentDto>allPostComments= postCommentService.getAllPostComments(pageRequest,postId,token);
        return allPostComments;
    }
}
