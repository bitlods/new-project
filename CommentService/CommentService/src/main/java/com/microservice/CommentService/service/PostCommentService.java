package com.microservice.CommentService.service;

import com.microservice.CommentService.dto.PostCommentBind;
import com.microservice.CommentService.dto.PostCommentDto;
import com.microservice.CommentService.exceptions.CustomException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCommentService {

    PostCommentDto createCommentService(PostCommentBind PostCommentBind, String token) throws CustomException;

    List<PostCommentDto> getAllPostComments(Pageable pageable,Long postId, String token) throws CustomException;
}
