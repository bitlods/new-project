package com.microservice.PostService.controller;

import com.microservice.PostService.dto.PostDto;
import com.microservice.PostService.entity.Post;
import com.microservice.PostService.exceptions.CustomException;
import com.microservice.PostService.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//@CrossOrigin({"http://localhost:5173/", "http://localhost:8005/"})
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/main/posts/createpost")
    public PostDto createPost(@RequestParam("desc")String description,
                             @RequestParam("image") MultipartFile image,
                           @RequestHeader("Authorization")String token) throws CustomException, IOException {
        Post newPost=new Post();
        newPost.setDescription(description);
        newPost.setImage(image.getBytes());
        return postService.createPostService(newPost,token);
    }
    @GetMapping("/posts/allPosts")
    public List<PostDto> getAllPosts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1") Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return postService.getAllPostsService(pageRequest);
    }
    @GetMapping("/main/posts/tokenuserposts")
    public List<PostDto> getTokenUserPosts(@RequestHeader("Authorization")String token,
                                      @RequestParam(defaultValue = "0")Integer page,
                                      @RequestParam(defaultValue = "1")Integer size) throws CustomException {
        PageRequest pageRequest=PageRequest.of(page,size);
        List<PostDto>allPosts=postService.getTokenUsersPosts(token,pageRequest);
        return allPosts;
    }
    @GetMapping("/main/posts/postbyuserid")
    public List<PostDto> getPostByUserId(@RequestHeader("Authorization")String token,
                                      @RequestParam(defaultValue = "0")Integer page,
                                      @RequestParam(defaultValue = "1")Integer size,
                                      @RequestParam("userId")Long userId) throws CustomException {
        PageRequest pageRequest=PageRequest.of(page,size);
        List<PostDto>allPosts=postService.getPostsByUserId(token,userId,pageRequest);
        return allPosts;
    }
    @PutMapping("/main/posts/liked")
    public PostDto likePost(@RequestHeader("Authorization")String token,
                            @RequestParam("postId") Long postId) throws CustomException {
        PostDto postDto= postService.likePost(token,postId);
        return postDto;
    }
    @GetMapping("/posts/allpostsnoti")
    public List<PostDto>allUsersAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/main/posts/postscount")
    public Integer tokenUserPostCount(@RequestHeader("Authorization")String token) throws CustomException {
        Integer count= postService.tokenUserPostCount(token);
        return count;
    }
    @GetMapping("/main/posts/allposts")
    public List<PostDto> getAllTokenUsersPosts(@RequestHeader("Authorization")String token) throws CustomException {
        List<PostDto>allPostDtos= postService.getAllUsersPosts(token);
        return allPostDtos;
    }
    @GetMapping("/main/posts/getpost")
    public PostDto getPostById(@RequestHeader("Authorization")String token,
            @RequestParam("postId")Long postId){
        return postService.getPostByIdService(token,postId);
    }
}
