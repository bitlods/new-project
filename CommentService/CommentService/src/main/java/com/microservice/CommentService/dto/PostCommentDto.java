package com.microservice.CommentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDto {
    private Long id;
    private String text;
    private UserDto user;
    private PostDto post;
}
