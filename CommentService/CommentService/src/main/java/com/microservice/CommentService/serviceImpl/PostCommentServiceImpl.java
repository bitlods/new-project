package com.microservice.CommentService.serviceImpl;

import com.microservice.CommentService.dto.PostCommentBind;
import com.microservice.CommentService.dto.PostCommentDto;
import com.microservice.CommentService.dto.PostDto;
import com.microservice.CommentService.dto.UserDto;
import com.microservice.CommentService.entity.PostComment;
import com.microservice.CommentService.exceptions.CustomException;
import com.microservice.CommentService.feign.PostInterface;
import com.microservice.CommentService.feign.UserInterface;
import com.microservice.CommentService.repository.PostCommentRepository;
import com.microservice.CommentService.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private PostInterface postInterface;
    @Autowired
    private PostCommentRepository postCommentRepository;
    @Override
    public PostCommentDto createCommentService(PostCommentBind postCommentBind, String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        UserDto existedUser=userInterface.getUserById(user.getId());
        PostDto post=postInterface.getPostById(token,postCommentBind.getPostId());
        if(existedUser!=null&&post!=null){
            PostComment postComment=new PostComment();
            postComment.setPost(postCommentBind.getPostId());
            postComment.setText(postCommentBind.getCommentText());
            postComment.setUser(existedUser.getId());
            PostComment dbPostComment= postCommentRepository.save(postComment);
            PostCommentDto postCommentDto=singleCommentDto(dbPostComment,token);
            return postCommentDto;
        }
        throw new CustomException("User or Post is Not Found ");
    }
    @Override
    public List<PostCommentDto> getAllPostComments(Pageable pageable, Long postId, String token) throws CustomException {
        UserDto user = userInterface.getTokenUser(token);
        UserDto existedUser = userInterface.getUserById(user.getId());
        PostDto post = postInterface.getPostById(token,postId);
        if (existedUser != null && post != null) {
            List<PostComment>pages=postCommentRepository.findCommentsByPost(pageable,postId);
            List<PostCommentDto>postCommentDtos=pages.stream().map((p)->singleCommentDto(p,token)).toList();
            return postCommentDtos;
        }
        throw new CustomException("Invalid Credentials Please try to Login then Fetch ");
    }
    public PostCommentDto singleCommentDto(PostComment postComment,String token){
        PostCommentDto postCommentDto=new PostCommentDto();
        postCommentDto.setId(postComment.getId());
        postCommentDto.setText(postComment.getText());
        postCommentDto.setUser(userInterface.getUserById(postComment.getUser()));
        postCommentDto.setPost(postInterface.getPostById(token,postComment.getPost()));
        return postCommentDto;
    }
}
