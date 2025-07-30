package com.microservice.PostService.serviceImpl;

import com.microservice.PostService.dto.NotificationDto;
import com.microservice.PostService.dto.PostDto;
import com.microservice.PostService.dto.UserDto;
import com.microservice.PostService.entity.Post;
import com.microservice.PostService.exceptions.CustomException;
import com.microservice.PostService.feign.NotificationInterface;
import com.microservice.PostService.feign.UserInterface;
import com.microservice.PostService.repository.PostRepository;
import com.microservice.PostService.service.PostService;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    UserInterface userInterface;
    @Autowired
    NotificationInterface notificationInterface;
    @Autowired
    private PostRepository postRepository;
    public PostDto createPostService(Post post,String token) throws CustomException {
        UserDto tokenUser=userInterface.getTokenUser(token);
        if(tokenUser==null){
            throw new CustomException("User not found with the Token");
        }
        Post newPost=new Post();
        newPost.setDescription(post.getDescription());
        newPost.setImage(post.getImage());
        newPost.setUserId(tokenUser.getId());
        Post savedPost=postRepository.save(newPost);
        tokenUser.setPassword(null);
        savedPost.setUserId(tokenUser.getId());
        PostDto postDto=singlePostDtos(savedPost);
        return postDto;
    }

    @Override
    public List<PostDto> getAllPostsService(Pageable pageable) {
        //with pagination
        List<Post>allPosts=postRepository.findAll(pageable).getContent();
        List<PostDto>allPostDtos=allPosts.stream().map((sPost)->singlePostDtos(sPost)).toList();
        return allPostDtos;
    }

    @Override
    public List<PostDto> getPostsByUserId(String token,Long userId,Pageable pageable) throws CustomException {
        //with pagination
        UserDto tokenUser=userInterface.getTokenUser(token);
        if(userId!=0&&tokenUser!=null){
            UserDto user=userInterface.getUserById(userId);
            List<Post>allPostsOfUser=postRepository.findPostsByUserId(user.getId(),pageable).stream().toList();
            List<PostDto>allPostsOfUserDtos=allPostsOfUser.stream().map((sPost)->singlePostDtos(sPost)).toList();
            return allPostsOfUserDtos;
        }
        throw new CustomException("Token is not Valid ");
    }

    @Override
    public PostDto likePost(String token, Long postId) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        Post dbPost=postRepository.findById(postId).get();
        if(user != null){
            if(dbPost.getLikes().contains(user.getId())){
                dbPost.getLikes().remove(user.getId());
                notificationInterface.likeNotification(token,postId);
            }
            else{
                dbPost.getLikes().add(user.getId());
                notificationInterface.likeNotification(token,postId);
            }
        }
        else {
            throw new CustomException("User is Invalid ");
        }
        Post post =postRepository.save(dbPost);
        PostDto postDto=singlePostDtos(post);
        return postDto;
    }
    @Override
    public Integer tokenUserPostCount(String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        List<Post>allTokenUserPosts=postRepository.findAllPostsByUserId(user.getId());
        return allTokenUserPosts.size();
    }
//
    @Override
    public List<PostDto> getAllUsersPosts(String token) throws CustomException {
        //without pagination
        UserDto user=userInterface.getTokenUser(token);
        List<Post> post= postRepository.findAllPostsByUserId(user.getId());
        List<PostDto>allUserPosts=post.stream().map((p)->singlePostDtos(p)).toList();
        return allUserPosts;
    }

    @Override
    public List<PostDto> getAllPosts() {
        //without pagination
        List<Post> allPosts=postRepository.findAll();
        List<PostDto>allPostsDto=allPosts.stream().map((post->singlePostDtos(post))).toList();
        return allPostsDto;
    }

    @Override
    public PostDto getPostByIdService(String token, Long postId) {
        UserDto user = userInterface.getTokenUser(token);
        if(user!=null){
            Post post = postRepository.findById(postId).get();
            PostDto postDto=singlePostDtos(post);
            return postDto;
        }
        throw new CustomException("User is Not Valid");
    }

    @Override
    public List<PostDto> getTokenUsersPosts(String token, Pageable pageable) {
        UserDto tokenUser=userInterface.getTokenUser(token);
        if(tokenUser!=null){
            List<Post>allPostsOfUser=postRepository.findPostsByUserId(tokenUser.getId(),pageable).stream().toList();
            List<PostDto>allPostDtos=allPostsOfUser.stream().map((p)->singlePostDtos(p)).toList();
            return allPostDtos;
        }
        throw new CustomException("Token is not Valid ");
    }
    public PostDto singlePostDtos(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setDescription(post.getDescription());
        postDto.setImage(post.getImage());
        List<UserDto> likedUsers = post.getLikes().stream()
                .map(userId -> userInterface.getUserById(userId))
                .collect(Collectors.toList());
        postDto.setLikes(likedUsers);
        postDto.setUser(userInterface.getUserById(post.getUserId()));
        return postDto;
    }
}
