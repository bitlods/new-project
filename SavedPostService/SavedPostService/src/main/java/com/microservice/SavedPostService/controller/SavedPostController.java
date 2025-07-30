package com.microservice.SavedPostService.controller;

import com.microservice.SavedPostService.dto.SavedPostDto;
import com.microservice.SavedPostService.entity.SavedPost;
import com.microservice.SavedPostService.exceptions.CustomException;
import com.microservice.SavedPostService.service.SavedPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("http://localhost:5173/")
@RequestMapping("/main/savedposts")
public class SavedPostController {
    @Autowired
    private SavedPostService savedPostService;
    @PostMapping("/save")
    public SavedPostDto savePost(@RequestHeader("Authorization")String token,
                                 @RequestParam("postId") Long postId) throws CustomException {
        return savedPostService.savePost(token,postId);
    }
    @DeleteMapping("/deleteSaved")
    public SavedPostDto deletePost(@RequestHeader("Authorization")String token,
                              @RequestParam("postId") Long postId) throws CustomException {
        return savedPostService.deleteSavedPost(token,postId);
    }
    @GetMapping("/allsaved")
    public List<SavedPostDto>allSavedPosts(@RequestHeader("Authorization")String token) throws CustomException {
        return savedPostService.getAllSavedPostsService(token);
    }
    @GetMapping("/getsavedpage")
    public List<SavedPostDto> getAllSavedPost(@RequestHeader("Authorization")String token,
                                            @RequestParam(defaultValue = "0")Integer page,
                                            @RequestParam(defaultValue = "1")Integer size) throws CustomException {
        PageRequest pageRequest=PageRequest.of(page,size);
        return savedPostService.getSavedPostsOfToken(token,pageRequest);
    }
}
