package com.microservice.SavedPostService.service;

import com.microservice.SavedPostService.dto.SavedPostDto;
import com.microservice.SavedPostService.entity.SavedPost;
import com.microservice.SavedPostService.exceptions.CustomException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SavedPostService {
    SavedPostDto savePost(String token, Long postId) throws CustomException;
    List<SavedPostDto> getSavedPostsOfToken(String token, Pageable pageable) throws CustomException;
    SavedPostDto deleteSavedPost(String token, Long postId) throws CustomException;
    List<SavedPostDto> getAllSavedPostsService(String token) throws CustomException;
}
