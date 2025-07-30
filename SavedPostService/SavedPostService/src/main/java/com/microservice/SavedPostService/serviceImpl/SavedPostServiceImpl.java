package com.microservice.SavedPostService.serviceImpl;

import com.microservice.SavedPostService.dto.PostDto;
import com.microservice.SavedPostService.dto.SavedPostDto;
import com.microservice.SavedPostService.dto.UserDto;
import com.microservice.SavedPostService.entity.SavedPost;
import com.microservice.SavedPostService.exceptions.CustomException;
import com.microservice.SavedPostService.feign.PostInterface;
import com.microservice.SavedPostService.feign.UserInterface;
import com.microservice.SavedPostService.repository.SavedPostRepository;
import com.microservice.SavedPostService.service.SavedPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedPostServiceImpl implements SavedPostService {
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private PostInterface postInterface;
    @Autowired
    private SavedPostRepository savedPostRepository;
    @Override
    public SavedPostDto savePost(String token, Long postId) throws CustomException {
        UserDto user = userInterface.getTokenUser(token);
        PostDto dbPost = postInterface.getPostById(token,postId);
        SavedPost dbSavedPost=savedPostRepository.findPostByPostAndUser(dbPost.getId(),user.getId());
        if(dbSavedPost!=null){
           SavedPost deletedPost=dbSavedPost;
           savedPostRepository.delete(dbSavedPost);
           SavedPostDto savedPostDto=singleSavedPostDto(deletedPost,token);
           return savedPostDto;
        }
        else{
            SavedPost savedPost=new SavedPost();
            savedPost.setPost(dbPost.getId());
            savedPost.setUser(user.getId());
            SavedPost newSavedPost=savedPostRepository.save(savedPost);
            SavedPostDto savedPostDto=singleSavedPostDto(newSavedPost,token);
            return savedPostDto;
        }
    }
    @Override
    public List<SavedPostDto> getSavedPostsOfToken(String token, Pageable pageable) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        List<SavedPost> savedPost=savedPostRepository.findPostByUser(user.getId(),pageable).getContent();
        List<SavedPostDto>allSavedPostDtos=savedPost.stream().map((s)->singleSavedPostDto(s,token)).toList();
        return allSavedPostDtos;
    }

    @Override
    public SavedPostDto deleteSavedPost(String token, Long postId) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        SavedPost savedPost=savedPostRepository.findPostByPostAndUser(postId,user.getId());
        savedPostRepository.delete(savedPost);
        SavedPostDto savedPostDto=singleSavedPostDto(savedPost,token);
        return savedPostDto;
    }

    @Override
    public List<SavedPostDto> getAllSavedPostsService(String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        List<SavedPost> allSavedPosts=savedPostRepository.findAllSavedPostsByUser(user.getId());
        List<SavedPostDto>allSavedPostsDtos=allSavedPosts.stream().map((s)->singleSavedPostDto(s,token)).toList();
        return  allSavedPostsDtos;
    }
    public SavedPostDto singleSavedPostDto(SavedPost post, String token){
        SavedPostDto savedPostDto=new SavedPostDto();
        savedPostDto.setId(post.getId());
        savedPostDto.setUser(userInterface.getUserById(post.getUser()));
        savedPostDto.setPost(postInterface.getPostById(token,post.getPost()));
        return savedPostDto;
    }
}