package com.microservice.StoryService.service;

import com.microservice.StoryService.dto.StoryDto;
import com.microservice.StoryService.entity.Story;
import com.microservice.StoryService.exceptions.CustomException;

import java.util.List;

public interface StoryService {
    StoryDto createStoryService(Story story, String token) throws CustomException;
    List<StoryDto> getAllUserStories(String token) throws CustomException;
    StoryDto getCurrentUserStoryService(String token) throws CustomException;
    StoryDto updateCurrentUserStory(Story story,String token) throws CustomException;
    String deleteCurrentUserStoryService(String token) throws CustomException;
}
