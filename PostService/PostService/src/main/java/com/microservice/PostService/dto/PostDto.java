package com.microservice.PostService.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String description;
    @Lob
    private byte[]image;
    private UserDto user;
    private List<UserDto> likes=new ArrayList<>();
}
