package com.microservice.SavedPostService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedPostDto {
    private Long id;
    private UserDto user;
    private PostDto post;
}
