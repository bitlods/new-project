package com.microservice.PostService.service;

import com.microservice.PostService.dto.PostDto;
import com.microservice.PostService.entity.Post;
import com.microservice.PostService.exceptions.CustomException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDto createPostService(Post post,String token) throws CustomException;
    List<PostDto> getAllPostsService(Pageable pageable);
    List<PostDto> getPostsByUserId( String token,Long userId,Pageable pageable) throws CustomException;
    PostDto likePost(String token, Long postId) throws CustomException;
    Integer tokenUserPostCount(String token) throws CustomException;
    List<PostDto> getAllUsersPosts(String token) throws CustomException;
    List<PostDto> getAllPosts();
    PostDto getPostByIdService(String token,Long postId);
    List<PostDto> getTokenUsersPosts(String token, Pageable pageable);
}
